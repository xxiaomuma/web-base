package pers.xiaomuma.base.dome.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Vehicle implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 组织架构id
     */
    private Integer orgId;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 车辆颜色id
     */
    private Integer colorId;

    /**
     * 车辆品牌id
     */
    private Integer brandId;

    /**
     * 车辆类型id
     */
    private Integer typeId;

    /**
     * 驾驶员id
     */
    private Integer driverId;

    /**
     * 车辆名称
     */
    private String name;

    /**
     * 车牌号
     */
    private String numberPlate;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 状态: -1->删除, 0->禁用, 1->开启
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 修改者
     */
    private Integer updateBy;


}
