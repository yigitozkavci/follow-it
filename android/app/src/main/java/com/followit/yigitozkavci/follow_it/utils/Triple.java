package com.followit.yigitozkavci.follow_it.utils;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class Triple<L, M, R> {
    public L left;
    public M middle;
    public R right;

    public Triple(L l, M m, R r) {
        this.left = l;
        this.middle = m;
        this.right = r;
    }
}
