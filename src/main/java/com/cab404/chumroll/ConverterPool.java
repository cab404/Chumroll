package com.cab404.chumroll;

/**
 * Manages instances of converters
 *
 * @author cab404
 */
class ConverterPool extends ConstructingPool<ViewConverter> {

    @Override
    protected ViewConverter<?> makeInstance(Class<? extends ViewConverter> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
