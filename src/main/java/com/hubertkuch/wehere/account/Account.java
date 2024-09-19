package com.hubertkuch.wehere.account;

import org.springframework.data.annotation.Id;

public record Account(@Id String id, String username, String password) {
}
