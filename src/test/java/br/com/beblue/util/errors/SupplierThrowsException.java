package br.com.beblue.util.errors;

@FunctionalInterface
public interface SupplierThrowsException<T> {

    T get() throws Exception;

}
