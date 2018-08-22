package com.zeed.paaro.viewcontroller;

import com.zeed.paaro.lib.apirequestmodel.WalletTransferRequest;
import com.zeed.paaro.lib.apiresponsemodel.WalletTransferRequestResponse;
import com.zeed.paaro.lib.models.Transaction;
import com.zeed.paaro.lib.models.WalletFundingTransaction;
import com.zeed.paaro.lib.models.WalletTransferTransaction;
import com.zeed.paaro.lib.repository.WalletTransferTransactionRepository;
import com.zeed.paaro.lib.services.TransferService;
import com.zeed.paaro.lib.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/view")
public class DashboardController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private WalletService walletService;


    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSACTIONS')")
    @RequestMapping(value = "/dashboard/findAllWalletTransferTransactionsPaged", method = RequestMethod.GET)
    public ModelAndView getWalletTransferTransactions(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "filter", required = false) String filter) {

        pageNo = (pageNo==null) ? 0 : pageNo;
        pageSize = (pageSize==null) ? 10 : pageSize;

        Page<WalletTransferTransaction> transactionPage = transferService.findWalletTransferTransactionPage(pageNo, pageSize, filter);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/transfer-requests");

        if (transactionPage == null) {
            modelAndView.addObject("transactions", new ArrayList<>());
            modelAndView.addObject("totalNoOfPages", 0);
            modelAndView.addObject("totalNoOfTransactions", 0);
            modelAndView.addObject("currentPage", 0);
            return modelAndView;
        }

        modelAndView.addObject("currentPage",transactionPage.getNumber());
        modelAndView.addObject("transactions",transactionPage.getContent());
        modelAndView.addObject("totalNoOfPages",transactionPage.getTotalPages());
        modelAndView.addObject("totalNoOfTransactions",transactionPage.getTotalElements());

        return modelAndView;

    }

    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSACTIONS')")
    @RequestMapping(value = "/dashboard/findAllWalletFundingTransactionsPaged", method = RequestMethod.GET)
    public ModelAndView getWalletFundTransactions(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "filter", required = false) String filter) {

        pageNo = (pageNo==null) ? 0 : pageNo;
        pageSize = (pageSize==null) ? 10 : pageSize;

        Page<WalletFundingTransaction> transactionPage = walletService.findWalletFundingTransactionPageWithFilter(pageNo, pageSize, filter);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/funding-requests");

        if (transactionPage == null) {
            modelAndView.addObject("transactions", new ArrayList<>());
            modelAndView.addObject("totalNoOfPages", 0);
            modelAndView.addObject("totalNoOfTransactions", 0);
            modelAndView.addObject("currentPage", 0);
            return modelAndView;
        }

        modelAndView.addObject("currentPage",transactionPage.getNumber());
        modelAndView.addObject("transactions",transactionPage.getContent());
        modelAndView.addObject("totalNoOfPages",transactionPage.getTotalPages());
        modelAndView.addObject("totalNoOfTransactions",transactionPage.getTotalElements());

        return modelAndView;

    }

}
