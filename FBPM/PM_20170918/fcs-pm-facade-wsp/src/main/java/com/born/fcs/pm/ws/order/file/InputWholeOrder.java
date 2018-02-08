package com.born.fcs.pm.ws.order.file;


import java.util.List;

public class InputWholeOrder {

    private List<FileInputOrder> file;

    private List<FileInputListOrder> LoanBusiness;//授信业务(基础卷)

    private List<FileInputListOrder> boforeLoan ;//授信后管理卷

    private List<FileInputListOrder> documentOfTitle;//权利凭证卷

    public List<FileInputOrder> getFile() {
        return file;
    }

    public void setFile(List<FileInputOrder> file) {
        this.file = file;
    }

    public List<FileInputListOrder> getLoanBusiness() {
        return LoanBusiness;
    }

    public void setLoanBusiness(List<FileInputListOrder> loanBusiness) {
        LoanBusiness = loanBusiness;
    }

    public List<FileInputListOrder> getBoforeLoan() {
        return boforeLoan;
    }

    public void setBoforeLoan(List<FileInputListOrder> boforeLoan) {
        this.boforeLoan = boforeLoan;
    }

    public List<FileInputListOrder> getDocumentOfTitle() {
        return documentOfTitle;
    }

    public void setDocumentOfTitle(List<FileInputListOrder> documentOfTitle) {
        this.documentOfTitle = documentOfTitle;
    }
}
