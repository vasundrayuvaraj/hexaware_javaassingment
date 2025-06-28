package com.hexaware.studentinformationsystem.model;



import java.math.BigDecimal;
import java.util.Date;
public class Payments {
        

	private int paymentId;
    private int studentId;
    private BigDecimal amount;
    private Date paymentDate;
    private Students student;

    public Payments(BigDecimal amount, Date paymentDate, Students student) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.student = student;
    }
   public Payments( Students student, int paymenttId,Date paymentDate, BigDecimal amount ) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.student = student;
    }
    public Payments(int studentId, BigDecimal amount, Date paymentDate, Students student) {
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.student = student;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Students getStudent() {
        return student;
    }
    @Override
   	public String toString() {
   		return "Payments [paymentId=" + paymentId + ", studentId=" + studentId + ", amount=" + amount + ", paymentDate="
   				+ paymentDate + ", student=" + student + "]";
   	}
 
}
