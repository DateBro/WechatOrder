package com.imooc.sell.service.Impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            // 这里也别忘了检查商品查询为空
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST_ERROR);
            }
            Integer resultStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            // 每次都要记得检查操作会不会有异常或其他问题
            if (resultStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(resultStock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            // 这里也别忘了检查商品查询为空
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST_ERROR);
            }
            Integer resultStock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(resultStock);
            repository.save(productInfo);
        }
    }
}
