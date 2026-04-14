package com.main.routes;

import java.util.List;

import com.main.auth.utils.Role;
import com.main.components.color;
import com.main.components.panelApps.containerPanel;
import com.main.components.panelApps.contentPanel;
import com.main.models.entity.accountDataStaff;
import com.main.models.entity.dataConvertion;
import com.main.models.entity.dataProduct;
import com.main.models.entity.dataSupplier;
import com.main.models.entity.dataTable;
import com.main.models.entity.entityDataStaff;
import com.main.models.entity.dataBobotKriteria;
import com.main.models.entity.listCompositionData;
import com.main.services.authDataStaff;
import com.main.views.dashboardAdmin.homeDashboardView;
import com.main.views.dashboardAdmin.parentDashboardAdmin;
import com.main.views.dashboardAdmin.Report.reportDashboardView;
import com.main.views.dashboardAdmin.calculation.calculationDashboardView;
import com.main.views.dashboardAdmin.convertion.convertionDashboardView;
import com.main.views.dashboardAdmin.convertion.convertionFormView;
import com.main.views.dashboardAdmin.product.productCompositionFormView;
import com.main.views.dashboardAdmin.product.productDashboardView;
import com.main.views.dashboardAdmin.product.productFormView;
import com.main.views.dashboardAdmin.staff.staffDashboardView;
import com.main.views.dashboardAdmin.staff.staffFormView;
import com.main.views.dashboardAdmin.supplier.supplierDashboardView;
import com.main.views.dashboardAdmin.supplier.supplierFormView;
import com.main.views.dashboardAdmin.table.tableDashboardView;
import com.main.views.dashboardAdmin.table.tableFormView;
import com.main.views.dashboardAdmin.transaction.transactionDashboardView;
import com.main.views.popUp.popUpConfrim;
import com.main.views.popUp.popUpFailed;
import com.main.views.popUp.popUpLogout;
import com.main.views.popUp.popUpSuccess;
import com.main.views.popUp.popUpDataKriteria.popUpBobotKriteria;
import com.main.views.popUp.popUpDataKriteria.popUpDataKriteria;
import com.main.views.popUp.popUpDataKriteria.popUpInputKriteria;
import com.main.views.popUp.popUpStaff.popUpDetailDataStaff;
import com.main.views.popUp.popUpStaff.popUpInputAccountStaff;
import com.main.views.popUp.popUpSupplier.popUpEditStatusSupplier;

public class dashboardAdminView extends containerPanel {

    private Role role;

    private parentDashboardAdmin parentDashboard;
    private mainFrame parentApp;
    private contentPanel lastContent;

    private entityDataStaff dataStaffToEdit = null;
    private accountDataStaff accountData = null;
    private dataSupplier dataSupplierToEdit = null;
    private dataProduct dataProductToEdit = null;
    private dataTable dataTableToEdit = null;
    private dataConvertion dataConvertionToEdit = null;
    private dataBobotKriteria dataBobotKriteriaToEdit = null;

    private boolean compositionModified = false;

    public dashboardAdminView(mainFrame parentApp, Role role) {
        super();
        this.parentApp = parentApp;
        this.role = role;
        setSize(1366, 768);
        setBackground(color.GREEN);
        parentDashboard = new parentDashboardAdmin(this, role);
        add(parentDashboard);

        parentDashboard.getNavbar().showHomeView();
    }

    public void showDashboardHome() {
        homeDashboardView dashboardHome = new homeDashboardView(this);
        lastContent = dashboardHome;
        parentDashboard.setContent(dashboardHome);
    }

    public void showDashboardProduct() {
        productDashboardView dashboardProduct = new productDashboardView(parentApp, this);
        dashboardProduct.loadAllProductCards();
        lastContent = dashboardProduct;
        parentDashboard.setContent(dashboardProduct);
    }

    public void showFormProduct() {
        productFormView formProduct = new productFormView(parentApp, this);

        if (dataProductToEdit != null) {
            formProduct.setFormProduct(dataProductToEdit);
            dataProductToEdit = null;
        }

        lastContent = formProduct;
        parentDashboard.setContent(formProduct);
    }

    public void showFormCompositionProduct(int idProduct, String imageProduct, String nameProduct,
            int price, String category, String description, List<listCompositionData> compositionList) {
        productCompositionFormView formCompositionProduct = new productCompositionFormView(this, idProduct,
                imageProduct, nameProduct, price, category, description);

        if (compositionList != null) {
            formCompositionProduct.setFormCompositionProduct(compositionList);
        }

        lastContent = formCompositionProduct;
        parentDashboard.setContent(formCompositionProduct);
    }

    public void showDashboardSupplier() {
        supplierDashboardView dashboardSupplier = new supplierDashboardView(parentApp, this);
        lastContent = dashboardSupplier;
        parentDashboard.setContent(dashboardSupplier);
    }

    public void showFormSupplier() {
        supplierFormView formSupplier = new supplierFormView(this);

        if (dataSupplierToEdit != null) {
            formSupplier.setFormSupplier(dataSupplierToEdit);
            dataSupplierToEdit = null;
        }

        lastContent = formSupplier;
        parentDashboard.setContent(formSupplier);
    }

    public void showDashboardTable() {
        tableDashboardView dashboardTable = new tableDashboardView(parentApp, this);
        lastContent = dashboardTable;
        parentDashboard.setContent(dashboardTable);
    }

    public void showFormTable() {
        tableFormView formTable = new tableFormView(this);

        if (dataTableToEdit != null) {
            formTable.setFormTable(dataTableToEdit);
            dataTableToEdit = null;
        }

        lastContent = formTable;
        parentDashboard.setContent(formTable);
    }

    public void showDashboardTransaction() {
        transactionDashboardView dashboardTransaction = new transactionDashboardView();
        lastContent = dashboardTransaction;
        parentDashboard.setContent(dashboardTransaction);
    }

    public void showDashboardConvertion() {
        convertionDashboardView dashboardConvertion = new convertionDashboardView(parentApp, this);
        lastContent = dashboardConvertion;
        parentDashboard.setContent(dashboardConvertion);
    }

    public void showFormConvertion() {
        convertionFormView formConvertion = new convertionFormView(this);

        if (dataConvertionToEdit != null) {
            formConvertion.setFormConvertion(dataConvertionToEdit);
            dataConvertionToEdit = null;
        }

        lastContent = formConvertion;
        parentDashboard.setContent(formConvertion);
    }

    public void showDashboardCalculation() {
        calculationDashboardView dashboardCalculation = new calculationDashboardView(this);
        lastContent = dashboardCalculation;
        parentDashboard.setContent(dashboardCalculation);
    }

    public void showDashboardBobotKriteria() {
        popUpDataKriteria dashboardBobotKriteria = new popUpDataKriteria(parentApp, this);
        parentDashboard.setContent(restoreLastContent());
        parentApp.showDashboardPopUp(dashboardBobotKriteria);

    }

    public void showFormDataKriteria() {
        popUpInputKriteria popUpBobotKriteria = new popUpInputKriteria(parentApp, this);

        if (dataBobotKriteriaToEdit != null) {
            popUpBobotKriteria.setFormBobotKriteria(dataBobotKriteriaToEdit);
            dataBobotKriteriaToEdit = null;
        }

        parentDashboard.setContent(restoreLastContent());
        parentApp.showFormPopUp(popUpBobotKriteria);
    }

    public void showBobotKriteriaPopUp() {
        popUpBobotKriteria popUp = new popUpBobotKriteria(parentApp);
        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(popUp);
    }

    public void showDashboardStaff() {
        staffDashboardView dashboardStaff = new staffDashboardView(parentApp, this);
        lastContent = dashboardStaff;
        parentDashboard.setContent(dashboardStaff);
    }

    public void showFormStaff() {
        staffFormView formStaff = new staffFormView(this);

        if (dataStaffToEdit != null) {
            formStaff.setFormData(dataStaffToEdit);
            dataStaffToEdit = null;
        }

        lastContent = formStaff;
        parentDashboard.setContent(formStaff);
    }

    public void showFormAccountStaff(
            String name,
            String email,
            String phoneNumber,
            String gender,
            String jobdesk,
            String address, boolean isEdit, int idStaff) {

        popUpInputAccountStaff popUpForm = new popUpInputAccountStaff(
                parentApp, this, name, email, phoneNumber, gender, jobdesk, address, isEdit, idStaff);

        this.accountData = authDataStaff.getDataAccountById(idStaff);
        this.dataStaffToEdit = new entityDataStaff(idStaff, name, email, phoneNumber, gender, jobdesk, address);

        System.out.println("ID Staff: " + idStaff);

        if (this.accountData != null) {
            popUpForm.setFormAccountData(this.accountData, dataStaffToEdit);
            accountData = null;
            System.out.println("[DEBUG] showFormAccountStaff: isEdit = " + isEdit + ", idStaff = " + idStaff);
        }

        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(popUpForm);
    }

    public void showDetailPopUpDataStaff(int idStaff) {
        popUpDetailDataStaff popUp = new popUpDetailDataStaff(parentApp, this, idStaff);
        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(popUp);
    }

    public void showDashboardReport() {
        reportDashboardView dashboardReport = new reportDashboardView();
        lastContent = dashboardReport;
        parentDashboard.setContent(dashboardReport);
    }

    public void showSuccessPopUp(String message) {
        popUpSuccess popUp = new popUpSuccess(parentApp);
        popUp.setNotificationMessage(message);
        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(popUp);
    }

    public void showFailedPopUp(String message) {
        popUpFailed popUp = new popUpFailed(parentApp);
        popUp.setNotificationMessage(message);
        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(popUp);
    }

    public popUpConfrim showConfrimPopUp(String message) {
        popUpConfrim popUp = new popUpConfrim(parentApp);
        popUp.setNotificationMessage(message);
        parentApp.showNotificationPopUp(popUp);
        parentDashboard.setContent(restoreLastContent());
        return popUp;
    }

    public void showPopUpEditStatusSupplier(int idSupplier) {
        popUpEditStatusSupplier popUp = new popUpEditStatusSupplier(parentApp, this, idSupplier);
        parentApp.showFormPopUp(popUp);
        parentDashboard.setContent(restoreLastContent());
    }

    public void showLogoutApp() {
        parentDashboard.setContent(restoreLastContent());
        parentApp.showNotificationPopUp(new popUpLogout(parentApp, role));
    }

    public contentPanel restoreLastContent() {
        return lastContent != null ? lastContent : new homeDashboardView(this);
    }

    public void resetLastContent() {
        parentDashboard.getNavbar().showHomeView();
        lastContent = null;
    }

    public void setDataProductToEdit(dataProduct dataProduct) {
        this.dataProductToEdit = dataProduct;
    }

    public void setCompositionModified(boolean modified) {
        this.compositionModified = modified;
    }

    public boolean isCompositionModified() {
        return this.compositionModified;
    }

    public void setDataSupplierToEdit(dataSupplier dataSupplier) {
        this.dataSupplierToEdit = dataSupplier;
    }

    public void setDataTableToEdit(dataTable dataTable) {
        this.dataTableToEdit = dataTable;
    }

    public void setDataConvertionToEdit(dataConvertion dataConvertion) {
        this.dataConvertionToEdit = dataConvertion;
    }

    public void setDataBobotKriteriaToEdit(dataBobotKriteria dataBobotKriteria) {
        this.dataBobotKriteriaToEdit = dataBobotKriteria;
    }

    public void setDataStaffToEdit(entityDataStaff dataStaff) {
        this.dataStaffToEdit = dataStaff;
    }

}
