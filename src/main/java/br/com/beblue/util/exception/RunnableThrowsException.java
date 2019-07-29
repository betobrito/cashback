package br.com.beblue.util.exception;

@FunctionalInterface
public interface RunnableThrowsException {

    void run() throws Exception;
}
