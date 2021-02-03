package com.groenify.api.database.methods.company;

import com.groenify.api.util.DateUtil;

import java.util.Date;

public interface CompanyMethods {

    String getCompanyName();

    String getCompanyDateString();

    default Date getCompanyDate() {
        return DateUtil.fromIsoNoMillis(getCompanyDateString());
    }

    String getCompanyUrl();
}
