package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.TransactionDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ITransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "TransactionController", description = "Set of methods related to transactions")
public class TransactionController {
  private ITransactionService transactionService;

  public TransactionController(
      ITransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/transaction")
  @ApiOperation(value = "API transaction", response = IPagedResponse.class)
  public  IPagedResponse<Object> createTransaction (@RequestBody TransactionDTO transactionDTO){
    transactionService.createTransaction(transactionDTO);
    return new IPagedResponse<>(new ResponseMessage<>());
  }
}
