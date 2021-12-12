package com.manduljo.ohou.mongo.controller.product;

import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.mongo.service.product.ZAdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo-admin/products", produces = AcceptType.API_V1)
public class ZAdminProductController {

  private final ZAdminProductService productService;

}
