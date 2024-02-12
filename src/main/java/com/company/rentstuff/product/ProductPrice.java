package com.company.rentstuff.product;

import com.company.rentstuff.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@JmixEntity
@Table(name = "PRODUCT_PRICE", indexes = {
        @Index(name = "IDX_PRODUCT_PRICE_PRODUCT", columnList = "PRODUCT_ID")
})
@Entity
public class ProductPrice extends StandardEntity {
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @PositiveOrZero
    @NotNull
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "UNIT", nullable = false)
    @NotNull
    private String unit;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PriceUnit getUnit() {
        return unit == null ? null : PriceUnit.fromId(unit);
    }

    public void setUnit(PriceUnit unit) {
        this.unit = unit == null ? null : unit.getId();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @InstanceName
    @DependsOnProperties({"amount", "unit"})
    public String getInstanceName() {
        return String.format("%s %s", amount, unit);
    }
}