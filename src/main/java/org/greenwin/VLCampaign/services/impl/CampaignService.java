package org.greenwin.VLCampaign.services.impl;

import org.greenwin.VLCampaign.model.Campaign;
import org.greenwin.VLCampaign.model.Category;
import org.greenwin.VLCampaign.proxies.TopicProxy;
import org.greenwin.VLCampaign.repository.CampaignRepository;
import org.greenwin.VLCampaign.services.ICampaignService;
import org.greenwin.VLCampaign.utils.CampaignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CampaignService implements ICampaignService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private TopicProxy topicProxy;

    @Autowired
    private CampaignUtil campaignUtil;



    public Campaign addCampaign(Campaign campaign){
        logger.info("### addCampaign method ###");
        return campaignRepository.save(campaign);
    }

    public Campaign findById(int id){
        logger.info("### findById method ###");
        Campaign campaign = campaignRepository.findById(id);
        //campaignUtil.fillWithTopic(campaign);
        return campaign;
    }

    /**
     * get ongoing campaigns
     * @return
     */
    public List<Campaign> getnCurrentCampaigns(){
        logger.info("### getnCurrentCampaigns method ###");
        return campaignRepository.getCampaignsByStartDateBeforeAndEndDateAfter(LocalDate.now(), LocalDate.now());
    }

    /**
     * get campaigns not started yet
     * @return
     */
    public List<Campaign> getCampaignsComingNext(){
        logger.info("### getCampaignsComingNext method ###");
        return campaignRepository.getCampaignsByStartDateAfter(LocalDate.now());
    }


    /**
     * select the n most recent campaigns
     * @param n
     * @return n most recent campaigns
     */
    public List<Campaign> getnMostRecent(int n){
        logger.info("### getMostRecent method ###");
        List<Campaign> allCampaigns = new ArrayList<>();
        List<Campaign> toDisplay = new ArrayList<>();
        campaignRepository.findAllByOrderByStartDateDesc().forEach(allCampaigns :: add);

        for (int i = 0; i < n; i++)
            toDisplay.add(allCampaigns.get(i));
        return toDisplay;
    }

    /**
     * get the campaign with the largest number of voters
     * @param n
     * @return n most popular campaigns
     */
    @Override
    public List<Campaign> getnMostPopular(int n) {
        logger.info("### getMostPopular method ###");
        List<Campaign> allCampaigns = new ArrayList<>();
        List<Campaign> toDisplay = new ArrayList<>();
        campaignRepository.findAll().forEach(allCampaigns :: add);

        for (int i = 0; i < n; i++)
            toDisplay.add(allCampaigns.get(i));

        return toDisplay;
    }

    @Override
    public List<Campaign> searchCampaignByKeyWord(String key) {
        return campaignRepository.getCampaignsByQuestionContainingIgnoreCase(key);
    }

    /**
     * update campaign
     * @param campaign
     * @return
     */
    public Campaign updateCampaign(Campaign campaign){
        logger.info("### updateCampaign method ###");
        return campaignRepository.save(campaign);
    }

    public List<Campaign> selectCampaigns(LocalDate start, LocalDate end, Category category){
        logger.info("### selectCampaigns method ###");
        List<Campaign> campaigns;
        if(category != null)
            campaigns = campaignRepository.findByCategoryAndStartDateBeforeAndEndDateAfter(category, start.minusDays(1), end.plusDays(1));
        else
            campaigns = campaignRepository.getCampaignsByStartDateBeforeAndEndDateAfter(start.minusDays(1), end.plusDays(1));

        return campaigns;
    }

}
