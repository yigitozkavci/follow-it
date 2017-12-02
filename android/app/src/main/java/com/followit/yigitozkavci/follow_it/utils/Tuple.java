package com.followit.yigitozkavci.follow_it.utils;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class Tuple<L, R> {
    public L left;
    public R right;

    public Tuple(L l, R r) {
        this.left = l;
        this.right = r;
    }

    public String toString() {
        return "Tuple [left=" + this.left.toString() + ", right=" + this.right.toString() + "]";
    }
}
