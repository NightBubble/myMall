package cn.edu.fudan.mall.service.serviceImpl;

import cn.edu.fudan.mall.mbg.mapper.PmsProductMapper;
import cn.edu.fudan.mall.mbg.model.PmsProduct;
import cn.edu.fudan.mall.mbg.model.PmsProductExample;
import cn.edu.fudan.mall.nosql.elsaticsearch.pojo.EsProduct;
import cn.edu.fudan.mall.nosql.elsaticsearch.repository.EsProductRepository;
import cn.edu.fudan.mall.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);
    @Autowired
    private EsProductRepository esProductRepository;
    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    public int importPmsProduct() {
        List<PmsProduct> pmsProductList = pmsProductMapper.selectByExample(new PmsProductExample());
        int res = 0;
        for (PmsProduct item : pmsProductList) {
            EsProduct esProduct = new EsProduct();
            esProduct.setId(item.getId());
            esProduct.setBrandName(item.getBrandName());
            esProduct.setName(item.getName());
            esProduct.setStock(item.getStock());
            esProduct.setPrice(item.getPrice());
            esProductRepository.save(esProduct);
            res++;
        }
        return res;
    }


    @Override
    public void deleteById(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            List<EsProduct> esProductList = new ArrayList();
            for(Long id:ids){
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esProductRepository.deleteAll(esProductList);
        }
    }

    @Override
    public EsProduct create(PmsProduct pmsProduct) {
        EsProduct esProduct = new EsProduct();
        BeanUtils.copyProperties(pmsProduct,esProduct);
        esProductRepository.save(esProduct);
        return esProduct;
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable page = PageRequest.of(pageNum,pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, page);
    }
}
