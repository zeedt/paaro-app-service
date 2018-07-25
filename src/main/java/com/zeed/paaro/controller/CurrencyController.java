package com.zeed.paaro.controller;

import com.zeed.paaro.lib.apirequestmodel.CurrencyRequest;
import com.zeed.paaro.lib.apiresponsemodel.CurrencyResponse;
import com.zeed.paaro.lib.enums.ApiResponseCode;
import com.zeed.paaro.lib.services.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/currency")
public class CurrencyController {

    private Logger logger = LoggerFactory.getLogger(CurrencyController.class.getName());

    @Autowired
    private CurrencyService currencyService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public CurrencyResponse addCurrency(@RequestBody CurrencyRequest currencyRequest) {

        try {

            return currencyService.addCurrency(currencyRequest);

        }  catch (Exception e) {

            logger.error("Error occured while adding cufrency due to ", e);
            return CurrencyResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while adding currency");

        }
    }

}
