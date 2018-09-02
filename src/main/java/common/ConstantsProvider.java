package common;

/**
 * Константы.
 */
public class ConstantsProvider {

    /**
     * Минимально допустимое количество строк-ответов в файле с ответами.
     */
    public static final int MIN_ANSWERS_ALLOWED = 3;

    /**
     * Коды ошибок.
     */
    public enum ErrorCodes {
        /**
         * Не найден файл ответов.
         */
        ERROR_ANSWERS_FILE_NOT_FOUND(10001),
        /**
         * Не удалось обработать файл ответов.
         */
        ERROR_ANSWERS_FILE_PARSING(10002),
        /**
         * Пустой файл ответов.
         */
        ERROR_ANSWERS_FILE_IS_EMPTY(10003),
        /**
         * Файл ответов должен содержать не менее трех строк.
         */
        ERROR_ANSWERS_FILE_HAS_WRONG_LINES_COUNT(10004),
        /**
         * Unsupported Encoding.
         */
        ERROR_UNSUPPORTED_ENCODING(10005),
        /**
         * Unhandled exception
         */
        ERROR_UNHANDLED_EXCEPTION(10006);
        /**
         * Код ошибки.
         */
        private final int code;

        /**
         * Инициализация.
         *
         * @param errorCode - Код ошибки
         */
        ErrorCodes(final int errorCode) {
            this.code = errorCode;
        }

        /**
         * Получить код ошибки.
         *
         * @return - код.
         */
        public int getCode() {
            return code;
        }
    }
}
