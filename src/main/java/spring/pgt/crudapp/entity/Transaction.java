package spring.pgt.crudapp.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.pgt.crudapp.data.ApprovalStatusEnum;
import spring.pgt.crudapp.data.ProductTypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")

    private String id;

    @Column(name = "product_name")

    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductTypeEnum productType;

    @Column(name = "start_time")
    private Long startTime;
    @Column(name = "end_time")
    private Long endTime;

    @Enumerated(EnumType.STRING)

    @Column(name = "approval_status")
    private ApprovalStatusEnum approvalStatus;

    // relasi ke user pembuat transaksi
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "nomor_induk")
    private User studentId;

    // relasi ke user yang jadi approval_pic
    @ManyToOne
    @JoinColumn(name = "approval_pic", referencedColumnName = "nomor_induk")
    private User ApprovalPic;
}
