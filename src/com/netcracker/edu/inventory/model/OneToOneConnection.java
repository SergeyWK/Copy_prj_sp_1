package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 10.11.16.
 */
public interface OneToOneConnection<A extends Device, B extends Device> extends Connection<A, B>, Connection.OneAPoint<A, B>, Connection.OneBPoint<A, B> {
}
