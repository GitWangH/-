package com.huatek.busi.dao.impl.phicom.plusmember;
   
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.huatek.busi.dao.phicom.plusmember.PhiPlusRightGiftBagDao;
import com.huatek.busi.model.phicom.plusmember.PhiPlusRight;
import com.huatek.busi.model.phicom.plusmember.PhiPlusRightGiftBag;
import com.huatek.frame.core.dao.AbstractDao;
import com.huatek.frame.core.page.DataPage;
import com.huatek.frame.core.page.QueryPage;



 /**
  * @ClassName: PhiPlusRightGiftBagDaoImpl
  * @Description: 
  * @author: Ken Bai
  * @Email : Ken_Bai@huatek.com
  * @date: 2018-01-10 14:53:38
  * @version: 1.0
  */

@Repository("PhiPlusRightGiftBagDao")
public class  PhiPlusRightGiftBagDaoImpl extends AbstractDao<Long,  PhiPlusRightGiftBag> implements  PhiPlusRightGiftBagDao {

    private Logger logger = LoggerFactory.getLogger(PhiPlusRightGiftBagDaoImpl.class);

    @Override
    public PhiPlusRightGiftBag findPhiPlusRightGiftBagById(Long id) {
        return super.getByKey(id);
    }

    @Override
    public void saveOrUpdatePhiPlusRightGiftBag( PhiPlusRightGiftBag entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void persistentPhiPlusRightGiftBag(PhiPlusRightGiftBag entity) {
        super.persistent(entity);
    }


    @Override
    public void deletePhiPlusRightGiftBag(PhiPlusRightGiftBag entity) {
        super.delete(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhiPlusRightGiftBag> findAllPhiPlusRightGiftBag() {
        return createEntityCriteria().list();
    }

    @Override
    public PhiPlusRightGiftBag findPhiPlusRightGiftBagByCondition(String condition) {
        Criteria criteria = createEntityCriteria();
        //TODO 查询条件
        //criteria.add(Restrictions.eq("name", condition));
        return (PhiPlusRightGiftBag) criteria.uniqueResult();
    }

    @Override
    public DataPage<PhiPlusRightGiftBag> getAllPhiPlusRightGiftBag(QueryPage queryPage) {
        return super.queryPageData(queryPage);
    }

	@Override
	public PhiPlusRightGiftBag findPhiPlusRightGiftBagByTaskType(String giftBagType) {
		String sql = "from PhiPlusRightGiftBag t where t.isValidate = 'on' and t.giftBagType = :giftBagType ";
		Query query = this.createQuery(sql);
		query.setParameter("giftBagType", giftBagType);
		PhiPlusRightGiftBag giftBag = null;
		List<PhiPlusRightGiftBag> giftBagsList= query.list();
		if(giftBagsList != null && giftBagsList.size() > 0 ){
			giftBag = giftBagsList.get(0);
		}
		return giftBag;
	}

}
