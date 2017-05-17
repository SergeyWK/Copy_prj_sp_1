package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.service.ConnectionService;

import java.io.*;

class ConnectionServiceImpl implements ConnectionService {


    public boolean isValidConnectionForOutputToStream(Connection connection) {
        return false;
    }


    public boolean isValidConnectionForWriteToStream(Connection connection) {
        return false;
    }


    public void writeConnection(Connection connection, Writer writer) throws IOException {

    }


    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        return null;
    }


    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {

    }


    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        return null;
    }
}
