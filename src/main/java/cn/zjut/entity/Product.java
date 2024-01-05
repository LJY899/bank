package cn.zjut.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    // 产品ID
    private Long productId;

    // 产品名称
    private String name;

    // 产品期限（月）
    private Integer tenure;

    // 年化利率（%）
    private BigDecimal annualInterestRate;

    // 起存金额（元）
    private BigDecimal minDepositAmount;

    // 单人限额（元）
    private BigDecimal singleLimitAmount;

    // 单日限额（元）
    private BigDecimal dailyLimitAmount;

    // 风险等级
    private String riskLevel;

    // 起息日
    private LocalDate startDate;

    // 到期日
    private LocalDate maturityDate;

    // 结息方式
    private String interestPayment;

    // 产品状态
    private String productStatus;

}
