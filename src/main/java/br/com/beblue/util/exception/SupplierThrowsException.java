package br.com.beblue.util.exception;

@FunctionalInterface
public interface SupplierThrowsException<T> {

    T get() throws Exception;

}
