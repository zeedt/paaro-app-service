package com.zeed.paaro.controller;

import com.zeed.paaro.lib.apirequestmodel.WalletTransferRequest;
import com.zeed.paaro.lib.apiresponsemodel.WalletTransferRequestResponse;
import com.zeed.paaro.lib.enums.ApiResponseCode;
import com.zeed.paaro.lib.services.TransferService;
import com.zeed.paaro.lib.services.WalletEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wallet")
public class WalletTransferController {

    @Autowired
    private TransferService transferService;


    private Logger logger = LoggerFactory.getLogger(WalletTransferController.class.getName());

    @RequestMapping(value = "transfer_to_account", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse fundWallet(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.logCustomerTransferRequest(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while logging transfer request ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while logging transfer request");
        }

    }

    @RequestMapping(value = "find_wallet_transfer_transactions", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllLoggedInUserWalletTransferTransactions(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByUserWallet(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions for logged in user ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transaction by email and currency for logged in user");
        }

    }
    @RequestMapping(value = "find_wallet_transfer_transactions/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllLoggedInUserWalletTransferTransactionsPaged(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByUserWalletPaged(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions for logged in user ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transaction by email and currency for logged in user");
        }

    }

    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL_AND_CURRENCY')")
    @RequestMapping(value = "find_wallet_transfer_transactions_by_email_and_currency", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllWalletTransferTransactionsByEmailAndCurrency(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByUserWalletAndEmail(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions by email and currency ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transactions by email and currency");
        }

    }
    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL_AND_CURRENCY')")
    @RequestMapping(value = "find_wallet_transfer_transactions_by_email_and_currency/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllWalletTransferTransactionsByEmailAndCurrencyPaged(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByUserWalletAndEmailPaged(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions by email and currency ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transactions by email and currency");
        }

    }

    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL')")
    @RequestMapping(value = "find_wallet_transfer_transactions_by_email", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllWalletTransferTransactionsByEmail(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByEmail(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions by email ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transactions for logged in user by email");
        }

    }
    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL')")
    @RequestMapping(value = "find_wallet_transfer_transactions_by_email/paged", method = RequestMethod.POST)
    @ResponseBody
    public WalletTransferRequestResponse findAllWalletTransferTransactionsByEmailPaged(@RequestBody WalletTransferRequest walletTransferRequest)  {

        try {
            return transferService.findAllTransferWalletTransactionsByEmailPaged(walletTransferRequest);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions by email with page ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transactions for logged in user by email");
        }

    }

    @RequestMapping(value = "find_all_unsettled_transactions_for_logged_in_user/customer_logged", method = RequestMethod.GET)
    @ResponseBody
    public WalletTransferRequestResponse findAllUnsettledTransactionsForLoggedInUser()  {

        try {
            return transferService.findAllCustomerLoggedTransferWalletTransactions();
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transaction by currency");
        }

    }
    @RequestMapping(value = "find_all_unsettled_transactions_for_logged_in_user/customer_logged/paged", method = RequestMethod.GET)
    @ResponseBody
    public WalletTransferRequestResponse findAllUnsettledTransactionsForLoggedInUserPaged(@RequestParam("pageSize") int pageSize, @RequestParam("pageNo") int pageNo)  {

        try {
            return transferService.findAllCustomerLoggedTransferWalletTransactionsPaged(pageSize, pageNo);
        } catch (Exception e) {
            logger.error("Error occurred while fetching wallet transfer transactions ", e);
            return WalletTransferRequestResponse.returnResponseWithCode(ApiResponseCode.SYSTEM_ERROR, "System error occurred while fetching wallet transfer transaction by currency");
        }

    }

}
