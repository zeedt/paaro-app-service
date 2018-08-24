package com.zeed.paaro.viewcontroller;

import com.zeed.paaro.lib.apirequestmodel.WalletRequest;
import com.zeed.paaro.lib.apirequestmodel.WalletTransferRequest;
import com.zeed.paaro.lib.apiresponsemodel.WalletResponse;
import com.zeed.paaro.lib.apiresponsemodel.WalletTransferRequestResponse;
import com.zeed.paaro.lib.audit.AuditLog;
import com.zeed.paaro.lib.repository.CurrencyRepository;
import com.zeed.paaro.lib.repository.WalletRepository;
import com.zeed.paaro.lib.services.TransferService;
import com.zeed.paaro.lib.services.WalletService;
import com.zeed.usermanagement.apimodels.ManagedUserModelApi;
import com.zeed.usermanagement.models.Authority;
import com.zeed.usermanagement.models.ManagedUser;
import com.zeed.usermanagement.models.ManagedUserAuthority;
import com.zeed.usermanagement.repository.AuthorityRepository;
import com.zeed.usermanagement.repository.ManagedUserAuthorityRepository;
import com.zeed.usermanagement.repository.ManagedUserRepository;
import com.zeed.usermanagement.request.UserDetailsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wiremock.com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/view")
public class UserViewController {

    @Autowired
    ManagedUserRepository managedUserRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserDetailsRequest userDetailsRequest;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ManagedUserAuthorityRepository managedUserAuthorityRepository;

    private Logger logger = LoggerFactory.getLogger(UserViewController.class.getName());

    @RequestMapping(value = "/users")
    public ModelAndView viewAuthorities(@RequestParam(name = "pageNo", required = false) Integer pageNo, @RequestParam(name = "filter", required = false) String filter) {

        pageNo = (pageNo == null) ? 0 : pageNo;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/users");

        Page<ManagedUser> managedUserPage = null;
        if (StringUtils.isEmpty(Strings.nullToEmpty(filter).trim())) {
            managedUserPage = managedUserRepository.findAllByEmailIsNotNull(new PageRequest(pageNo,10, Sort.Direction.DESC, "id"));
        } else {
            filter = "%" + filter.trim() + "%";
            managedUserPage = managedUserRepository.findAllByFirstNameIsLikeOrLastNameIsLike(filter, filter,new PageRequest(pageNo,10, Sort.Direction.DESC, "id"));
        }

        if (managedUserPage == null) {
            modelAndView.addObject("users",new ArrayList<>());
            modelAndView.addObject("totalNoOfPages",0);
            modelAndView.addObject("totalElements",0);
            modelAndView.addObject("currentPage",pageNo);
            modelAndView.addObject("filterValue",Strings.nullToEmpty(filter).replace("%",""));
            return modelAndView;
        }
        modelAndView.addObject("users",managedUserPage.getContent());
        modelAndView.addObject("totalNoOfPages",managedUserPage.getTotalPages());
        modelAndView.addObject("totalElements",managedUserPage.getTotalElements());
        modelAndView.addObject("currentPage",pageNo);
        modelAndView.addObject("filterValue",Strings.nullToEmpty(filter).replace("%",""));

        return modelAndView;
    }

    @RequestMapping(value = "/user/getUserDetailsByEmail")
    public ModelAndView getUserDetailsByEmail(@RequestParam("email") String email) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/userDetailsView");

        ManagedUserModelApi userModelApi = userDetailsRequest.getManagedUserDetailsByEmail(email);

        if (userModelApi == null) {
            return modelAndView;
        }

        List<Authority> authorities = authorityRepository.findAllByAuthorityIsNotNull();
        modelAndView.addObject("authorities",authorities);
        modelAndView.addObject("email",email);
        modelAndView.addObject("userDetails",userModelApi);
        modelAndView.addObject("wallets",walletRepository.findAllByManagedUser_Email(email));

        return modelAndView;

    }


    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL')")
    @RequestMapping(value = "/user/findAllWalletTransferTransactionsByEmailPaged", method = RequestMethod.POST)
    public ModelAndView getUserTransactionsByEmail(@RequestBody WalletTransferRequest walletTransferRequest) {

        WalletTransferRequestResponse transferRequestResponse = null;

        if (StringUtils.isEmpty(walletTransferRequest.getFilter())) {
            transferRequestResponse = transferService.findAllTransferWalletTransactionsByEmailPaged(walletTransferRequest);
        } else {
            transferRequestResponse = transferService.findAllTransferWalletTransactionsByEmailPagedWithFilter(walletTransferRequest);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/userTransferTransactionsView");
        modelAndView.addObject("currentPage",walletTransferRequest.getPageNo());

        if (transferRequestResponse == null || transferRequestResponse.getWalletTransferTransactionPage() == null) {

            modelAndView.addObject("transactions",new ArrayList<>());
            modelAndView.addObject("totalNoOfPages",0);
            modelAndView.addObject("totalNoOfTransactions",0);
            return modelAndView;
        }

        modelAndView.addObject("transactions",transferRequestResponse.getWalletTransferTransactionPage().getContent());
        modelAndView.addObject("totalNoOfPages",transferRequestResponse.getWalletTransferTransactionPage().getTotalPages());
        modelAndView.addObject("totalNoOfTransactions",transferRequestResponse.getWalletTransferTransactionPage().getTotalElements());

        return modelAndView;

    }

    @PreAuthorize(value = "hasAnyAuthority('FETCH_TRANSFER_TRANSACTIONS_BY_EMAIL')")
    @RequestMapping(value = "/user/findAllFundWalletTransactionsByEmailPaged", method = RequestMethod.POST)
    public ModelAndView getUserFundWalletTransactionsByEmail(@RequestBody WalletRequest walletRequest) {

        String ipAddress = AuditLog.getIpAddressOfMachine();

        logger.info("IpAddress is " + ipAddress);

        WalletResponse walletResponse = null;

        walletResponse = walletService.findALlFundingWalletTransactionsByEmailPaged(walletRequest);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/userFundWalletTransactions");
        modelAndView.addObject("currentPage",walletRequest.getPageNo());

        if (walletResponse == null || walletResponse.getWalletFundingTransactionPage() == null) {

            modelAndView.addObject("transactions",new ArrayList<>());
            modelAndView.addObject("totalNoOfPages",0);
            modelAndView.addObject("totalNoOfTransactions",0);
            return modelAndView;
        }

        modelAndView.addObject("transactions",walletResponse.getWalletFundingTransactionPage().getContent());
        modelAndView.addObject("totalNoOfPages",walletResponse.getWalletFundingTransactionPage().getTotalPages());
        modelAndView.addObject("totalNoOfTransactions",walletResponse.getWalletFundingTransactionPage().getTotalElements());

        return modelAndView;

    }

    @PreAuthorize(value = "hasAnyAuthority('MAP_AUTHORITIES_TO_USER')")
    @RequestMapping(value = "/user/authority-to-user-view", method = RequestMethod.POST)
    public ModelAndView authorityToUserView() {

        List<Authority> authorities = authorityRepository.findAllByAuthorityIsNotNull();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/addauthoritytouser");

            modelAndView.addObject("authorities",authorities);

        return modelAndView;

    }

    @ResponseBody
    @PreAuthorize(value = "hasAnyAuthority('DELETE_USER_AUTHORITY')")
    @RequestMapping(value = "/user/delete-user-autority/{userId}/{authorityId}", method = RequestMethod.GET)
    public String deleteUserAuthority(@PathVariable("userId") Long userId, @PathVariable("authorityId") Long authorityId ) {

        List<ManagedUserAuthority> managedUserAuthority = managedUserAuthorityRepository.findAllByManagedUserIdAndAuthorityId(userId, authorityId);

        if (CollectionUtils.isEmpty(managedUserAuthority)) {
            return "Authority not papped to user";
        }

        managedUserAuthorityRepository.delete(managedUserAuthority);

        return "Authority successfully deleted for user";
    }


    @PreAuthorize(value = "hasAnyAuthority('CREATE_ADMIN_USER')")
    @RequestMapping(value = "/user/createadminuserview")
    public ModelAndView createAdminUserView() throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/createAdminUser");

        return modelAndView;

    }

    @PreAuthorize(value = "hasAnyAuthority('INITIATE_TRANSFER_REQUEST_FOR_USER')")
    @RequestMapping(value = "/user/showAddWalletTransactionView")
    public ModelAndView showAddWalletTransactionView(@RequestParam("email") String email) throws Exception {


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersview/addTransferTransaction");
        modelAndView.addObject("wallets",walletService.findWalletsByEmail(email).getWalletList());
        modelAndView.addObject("currencies",currencyRepository.findAll());

        return modelAndView;

    }

    @ResponseBody
    @RequestMapping (value = "/logout", method = RequestMethod.GET)
    public String logout() throws Exception {
        try {
            userDetailsRequest.logout();
        } catch (Exception e) {
            logger.error("Error occured while logging out due to " + e);
        }

        return "login";

    }


}
