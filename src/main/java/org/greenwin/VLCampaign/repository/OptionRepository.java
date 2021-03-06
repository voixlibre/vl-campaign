package org.greenwin.VLCampaign.repository;

import org.greenwin.VLCampaign.model.Campaign;
import org.greenwin.VLCampaign.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

    List<Option> findAllByCampaign(Campaign campaign);

    Option getById(int id);
}
