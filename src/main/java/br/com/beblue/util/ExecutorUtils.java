package br.com.beblue.util;

import br.com.beblue.util.exception.RunnableThrowsException;
import br.com.beblue.util.exception.SupplierThrowsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.beblue.util.Constantes.MensagemLog.MSG_LOG_EXECUTAR_LOGANDO_EXCECAO;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public interface ExecutorUtils {

    Logger LOG = LoggerFactory.getLogger(ExecutorUtils.class);

    static void executarLogandoExcecao(RunnableThrowsException comando) {
        try {
            comando.run();
        } catch (Exception e) {
            LOG.error(MSG_LOG_EXECUTAR_LOGANDO_EXCECAO, getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

    static <T> T executarIgnorandoExcecaoComValorDefault(SupplierThrowsException<T> comando, T valorDefault) {
        try {
            return comando.get();
        } catch (Exception e) {
            LOG.error(MSG_LOG_EXECUTAR_LOGANDO_EXCECAO, getStackTrace(e));
            return valorDefault;
        }
    }
}
