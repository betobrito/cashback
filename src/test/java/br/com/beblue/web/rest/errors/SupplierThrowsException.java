package br.com.beblue.web.rest.errors;

@FunctionalInterface
public interface SupplierThrowsException<T> {

    T get() throws Exception;

}
