/*
 *  Copyright (C) 2010-2025 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.gui.debugger;

import com.jpexs.helpers.utf8.Utf8Helper;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author JPEXS
 */
public class Debugger {

    public static final int MSG_STRING = 0;

    public static final int MSG_LOADER_URL = 1;

    public static final int MSG_LOADER_BYTES = 2;

    public static final int MSG_DUMP_BYTEARRAY = 3;

    public static final int MSG_REQUEST_BYTEARRAY = 4;

    public static final int MSG_LOADER_URL_INFO = 5;

    public static final int MSG_LOADER_MODIFY_BYTES = 6;

    private static final Set<DebugListener> listeners = new HashSet<>();

    private static Logger logger = Logger.getLogger(Debugger.class.getName());

    private static boolean active = false;

    public static boolean isActive() {
        return active;
    }

    public synchronized void addMessageListener(DebugListener l) {
        listeners.add(l);
    }

    public synchronized void removeMessageListener(DebugListener l) {
        listeners.remove(l);
    }

    private static class DebugHandler extends Thread {

        private final Socket s;

        private final int serverPort;

        private static int maxid = 0;

        private final int id;

        public boolean finished = false;

        private final Map<String, String> parameters = new HashMap<>();

        public String getParameter(String name, String defValue) {
            if (parameters.containsKey(name)) {
                return parameters.get(name);
            }
            return defValue;
        }

        public int getVersionMajor() {
            return Integer.parseInt(getParameter("debug.version.major", "1"));
        }

        public int getVersionMinor() {
            return Integer.parseInt(getParameter("debug.version.major", "0"));
        }

        public boolean hasMsgType() {
            return getVersionMajor() > 1 || getVersionMinor() > 0;
        }

        public DebugHandler(int serverPort, Socket s) {
            this.s = s;
            id = maxid++;
            this.serverPort = serverPort;
        }

        public void cancel() {
            try {
                s.close();
            } catch (IOException ex) {
                //ignore
            }
        }

        private int readType(InputStream is) throws IOException {
            int type = is.read();
            if (type == -1) {
                throw new EOFException();
            }
            return type;
        }

        private void writeBytes(OutputStream os, byte[] data) throws IOException {
            os.write((data.length >> 24) & 0xff);
            os.write((data.length >> 16) & 0xff);
            os.write((data.length >> 8) & 0xff);
            os.write(data.length & 0xff);
            os.write(data);
        }

        private byte[] readBytes(InputStream is) throws IOException {
            int len = is.read();
            if (len == -1) {
                throw new EOFException();
            }
            int len2 = is.read();
            if (len2 == -1) {
                throw new EOFException();
            }
            int len3 = is.read();
            if (len3 == -1) {
                throw new EOFException();
            }
            int len4 = is.read();
            if (len4 == -1) {
                throw new EOFException();
            }
            len = (len << 24) + (len2 << 16) + (len3 << 8) + len4;
            byte[] data = new byte[len];
            int cnt;
            int off = 0;
            while (len > 0 && (cnt = is.read(data, off, len)) > 0) {
                len -= cnt;
                off += cnt;
            }
            return data;
        }

        private String readString(InputStream is) throws IOException {
            int len = is.read();
            if (len == -1) {
                throw new EOFException();
            }
            int len2 = is.read();
            if (len2 == -1) {
                throw new EOFException();
            }
            len = (len << 8) + len2;

            byte[] buf = new byte[len];
            for (int i = 0; i < len; i++) {
                int rd = is.read();
                if (rd == -1) {
                    throw new EOFException();
                }
                buf[i] = (byte) rd;
            }
            return new String(buf, Utf8Helper.charset);
        }

        @Override
        public void run() {
            String clientName = Integer.toString(id);
            try (InputStream is = s.getInputStream(); OutputStream os = s.getOutputStream()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int c;
                do {
                    c = is.read();
                    if (c != 0) {
                        baos.write(c);
                    }

                } while (c > 0);
                String ret = baos.toString("UTF-8");

                if (ret.equals("<policy-file-request/>")) {
                    os.write(("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" secure=\"false\" /></cross-domain-policy>").getBytes("UTF-8"));
                } else {
                    if (!ret.isEmpty()) {
                        logger.log(Level.FINER, "Connected. Header: {0}", ret);
                        String[] param = (ret.contains(";") ? ret.split(";") : new String[]{ret});
                        for (String p : param) {
                            if (p.contains("=")) {
                                String key = p.substring(0, p.indexOf('='));
                                String val = p.substring(p.indexOf('=') + 1);
                                parameters.put(key, val);
                            } else {
                                parameters.put(p, "true");
                            }
                        }
                        active = true;
                    }
                    boolean hasType = hasMsgType();
                    logger.log(Level.FINER, "reading name...");
                    String name = readString(is);
                    logger.log(Level.FINER, "name = {0}", name);
                    if (!name.isEmpty()) {
                        clientName = name;
                    }
                    while (true) {
                        int type = 0;
                        logger.finer("reading type...");
                        if (hasType) {
                            type = readType(is);
                        }
                        logger.log(Level.FINE, "received type {0}", type);
                        switch (type) {
                            case MSG_STRING:
                                logger.finer("reading string...");
                                ret = readString(is);
                                logger.finer("informing listeners...");
                                for (DebugListener l : listeners) {
                                    l.onMessage(clientName, ret);
                                }
                                logger.finer("listeners informed");
                                break;
                            case MSG_LOADER_URL:
                                logger.finer("reading string...");
                                ret = readString(is);
                                logger.finer("informing listeners...");
                                for (DebugListener l : listeners) {
                                    l.onLoaderURL(clientName, ret);
                                }
                                logger.finer("listeners informed");
                                break;
                            case MSG_LOADER_BYTES:
                                logger.finer("reading bytes...");
                                byte[] retB = readBytes(is);
                                logger.finer("informing listeners...");
                                for (DebugListener l : listeners) {
                                    l.onLoaderBytes(clientName, retB);
                                }
                                logger.finer("listeners informed");
                                break;
                            case MSG_DUMP_BYTEARRAY:
                                logger.finer("reading bytes...");
                                byte[] retBa = readBytes(is);
                                logger.finer("informing listeners...");
                                for (DebugListener l : listeners) {
                                    l.onDumpByteArray(clientName, retBa);
                                }
                                logger.finer("listeners informed");
                                break;
                            case MSG_REQUEST_BYTEARRAY:
                                logger.finer("checking listeners for data...");
                                boolean dataFound = false;
                                for (DebugListener l : listeners) {
                                    byte[] data = l.onRequestBytes(clientName);
                                    if (data != null) {
                                        logger.finer("found listener with data");
                                        logger.log(Level.FINER, "writing data.length = {0}", data.length);
                                        writeBytes(os, data);
                                        logger.finer("data written");
                                        dataFound = true;
                                        break;
                                    }
                                }
                                if (!dataFound) {
                                    logger.finer("listener not found, writing empty array");
                                    writeBytes(os, new byte[0]);
                                }
                                os.flush();
                                logger.finer("listeners checked");
                                break;
                            case MSG_LOADER_URL_INFO:
                                logger.finer("reading string...");
                                ret = readString(is);
                                logger.finer("informing listeners...");
                                for (DebugListener l : listeners) {
                                    l.onLoaderURLInfo(clientName, ret);
                                }
                                logger.finer("listeners informed");
                                break;
                            case MSG_LOADER_MODIFY_BYTES:
                                logger.finer("reading url(string)...");
                                String url = readString(is);
                                logger.finer("reading bytes...");
                                byte[] inputBytes = readBytes(is);

                                logger.finer("checking listeners for data...");
                                boolean modifyDataFound = false;
                                for (DebugListener l : listeners) {
                                    if (l.isModifyBytesSupported()) {
                                        l.onLoaderModifyBytes(clientName, inputBytes, url, new DebugLoaderDataModified() {
                                            @Override
                                            public void dataModified(byte[] data) {
                                                if (data != null) {
                                                    logger.finer("got modified data");
                                                    logger.log(Level.FINER, "writing data.length = {0}", data.length);
                                                    try {
                                                        writeBytes(os, data);
                                                        os.flush();
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(Debugger.class.getName()).log(Level.SEVERE, null, ex);
                                                    }

                                                    logger.finer("data written");
                                                } else {
                                                    logger.finer("got empty modified data, writing original array");
                                                    try {
                                                        writeBytes(os, inputBytes);
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(Debugger.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    logger.finer("data written");
                                                }
                                            }
                                        });
                                        modifyDataFound = true;
                                        break;
                                    }
                                }
                                if (!modifyDataFound) {
                                    logger.finer("listener not found, writing original array");
                                    writeBytes(os, inputBytes);
                                    logger.finer("data written");
                                }
                                os.flush();
                                logger.finer("listeners checked");
                                break;
                        }
                    }
                }

            } catch (IOException ex) {
                logger.log(Level.FINER, "IOException in injected debugger thread: {0}", ex.getMessage());
                //ignore
            }
            try {
                s.close();
            } catch (IOException ex) {
                logger.log(Level.FINER, "Socked close exception in injected debugger thread: {0}", ex.getMessage());
                //ignore
            }
            finished = true;
            active = false;
            logger.log(Level.FINER, "Calling onFinish");
            for (DebugListener l : listeners) {
                l.onFinish(clientName);
            }
            logger.log(Level.FINER, "Injected debugger finished");
        }
    }

    private static class DebugServerThread extends Thread {

        private final int port;

        private ServerSocket ss;

        private final Map<Integer, DebugHandler> handlers = new WeakHashMap<>();

        public DebugServerThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ss = new ServerSocket(port, 50, InetAddress.getByName("localhost"));
                ss.setReuseAddress(true);
                while (true) {
                    Socket s = ss.accept();
                    DebugHandler h = new DebugHandler(port, s);
                    handlers.put(h.id, h);
                    h.start();
                }
            } catch (IOException ex) {
                //ignore
            }
        }
    }

    private final int port;

    public Debugger(int port) {
        this.port = port;
    }

    private DebugServerThread server = null;

    public synchronized void start() {
        if (server == null) {
            server = new DebugServerThread(port);
            server.start();
        }
    }

    public synchronized boolean isRunning() {
        return server != null;
    }

    public int getPort() {
        return port;
    }

    public synchronized void stop() {
        if (server != null) {
            try {
                server.ss.close();
            } catch (IOException ex) {
                //ignore
            }
            for (DebugHandler h : server.handlers.values()) {
                h.cancel();
            }
            server.handlers.clear();
            server = null;
        }
    }
}
