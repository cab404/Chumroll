package com.cab404.chumroll.proxies;

/**
 * Implement this interface in your converter if you want titles and custom width in
 * {@link ChumrollPagerAdapter}.
 *
 * @author cab404
 */
public interface PagerMetaProvider {
    float getViewPagerPageWidth(ChumrollPagerAdapter adapter, int position);
    String getViewPagerPageTitle(ChumrollPagerAdapter adapter, int position);
}
