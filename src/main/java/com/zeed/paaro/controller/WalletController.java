package com.zeed.paaro.controller;

import com.zeed.paaro.lib.apirequestmodel.WalletRequest;
import com.zeed.paaro.lib.apiresponsemodel.WalletResponse;
import com.zeed.paaro.lib.enums.ApiResponseCode;
import com.zeed.paaro.lib.services.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    Logger logger = LoggerFactory.getLogger(WalletController.class.getName());

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public WalletResponse findWalletById(@RequestParam("id") Long id)  {

        try {
            return walletService.findWalletById(id);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet by Id", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by id");
        }
    }


    @RequestMapping(value = "/get_by_user_id", method = RequestMethod.GET)
    @ResponseBody
    public WalletResponse findWalletByUserId(@RequestParam("userid") Long userId)  {

        try {
            return walletService.findWalletsByUserId(userId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet by user Id", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by user id");
        }
    }


    @RequestMapping(value = "/get_by_email", method = RequestMethod.GET)
    @ResponseBody
    public WalletResponse findWalletByEmail(@RequestParam("email") String email)  {

        try {
            return walletService.findWalletsByEmail(email);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet by email", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by email");
        }
    }

    @RequestMapping(value = "get_by_currency", method = RequestMethod.GET)
    @ResponseBody
    public WalletResponse findWalletByCurrency(@RequestParam("currency") String currency)  {

        try {
            return walletService.findWalletsByCurrency(currency);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet by currency", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency");
        }
    }

    @RequestMapping(value = "get_loggedin_user_wallets", method = RequestMethod.GET)
    @ResponseBody
    public WalletResponse findLoggedInUserWallets()  {

        try {
            return walletService.findLoggedInUserWallets();
        } catch (Exception e) {
            logger.error("Error occurred while fetching logged in user wallets ", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching logged in user wallet");
        }
    }

    @RequestMapping(value = "create_wallet", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse addWallet(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.addWallet(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while mapping wallet to user", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency");
        }
    }

    
    @RequestMapping(value = "fund_wallet", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse fundWallet(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.fundWallet(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while funding wallet ", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while funding wallet");
        }
        
    }

    @RequestMapping(value = "find_wallet_funding_transactions", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse findAllWalletTransactions(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.findAllFundingWalletTransactionsByUserWallet(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet funding transactions by currency ", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency");
        }

    }

    @RequestMapping(value = "find_wallet_funding_transactions/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse findAllWalletTransactionsPaged(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.findALlFundingWalletTransactionsByUserWalletPaged(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching paged wallet funding transactions by currency", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency");
        }

    }

    @RequestMapping(value = "find_wallet_funding_transactions/by_email/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse findAllWalletTransactionsByEmailPaged(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.findALlFundingWalletTransactionsByUserWalletPaged(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching paged wallet funding transactions by currency", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency");
        }

    }

    @RequestMapping(value = "find_wallet_funding_transactions_by_email_and_currency", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse findAllWalletTransactionsByEmailAndCurrency(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.findALlFundingWalletTransactionsByEmailAndCurrency(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet funding transactions by email and currency", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency and email");
        }

    }
    @RequestMapping(value = "find_wallet_funding_transactions_by_email_and_currency/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletResponse findAllWalletTransactionsByEmailAndCurrencyPaged(@RequestBody WalletRequest walletRequest)  {

        try {
            return walletService.findALlFundingWalletTransactionsByEmailAndCurrencyPaged(walletRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching paged wallet funding transactions by email and currency", e);
            return WalletResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet by currency and email");
        }

    }


    


}
