package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.dto.LinkDTO;
import top.frium.pojo.entity.Link;
import top.frium.pojo.vo.LinkVO;

import java.util.List;

/**
 * @date 2025-03-18 15:40:10
 * @description
 */
public interface LinkService extends IService<Link> {
    void addLink(LinkDTO linkDTO);

    void deleteLink(List<Long> linkIds);

    void updateLink(LinkDTO linkDTO);

    List<Link> getLinks();
    List<LinkVO> getLinkVOs();
}
