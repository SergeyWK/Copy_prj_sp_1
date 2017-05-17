package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.service.ConnectionService;

import java.io.*;

class ConnectionServiceImpl implements ConnectionService {

    @Override
    public boolean isValidConnectionForOutputToStream(Connection connection) {
        return false;
    }

    @Override
    public boolean isValidConnectionForWriteToStream(Connection connection) {
        return false;
    }

    @Override
    public void writeConnection(Connection connection, Writer writer) throws IOException {

    }

    @Override
    public Connection readConnection(Reader reader) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void outputConnection(Connection connection, OutputStream outputStream) throws IOException {

    }

    @Override
    public Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException {
        return null;
    }
}
