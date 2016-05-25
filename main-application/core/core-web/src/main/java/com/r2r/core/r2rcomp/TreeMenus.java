/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.r2rcomp;

import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.navegacion.Sistema;
import com.r2r.core.profile.ProfileEjb;
import com.r2r.core.security.SecurityCatalogs;
import com.r2r.core.util.RecursiveMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Alan Hdez
 */
@FacesComponent("treeMenus")
public class TreeMenus extends UINamingContainer {

    @EJB
    private SecurityCatalogs securityCatalogs;

    @Inject
    private ProfileEjb profileEjb;

    private List<Menu> listMenu;

    public Integer getIdProfile() {
        return (Integer) getStateHelper().eval("idProfile");
    }

    public TreeNode getRootNode() {
        return (TreeNode) getStateHelper().eval("rootNode");
    }

    public void setRootNode(TreeNode rootNode) {
        getStateHelper().put("rootNode", rootNode);
    }

    public List<Menu> getSelectionMenus() {
        return (List<Menu>) getStateHelper().eval("selectionMenus");
    }

    public void setSelectionMenus(List<Menu> selectionMenus) {
        getStateHelper().put("selectionMenus", selectionMenus);
    }

    @Size(min = 1)
    public TreeNode[] getSelectedNodes() {
        return (TreeNode[]) getStateHelper().eval("selectedNodes");
    }

    @Size(min = 1)
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        getStateHelper().put("selectedNodes", selectedNodes);
    }

    /**
     * Returns the component family of {@link UINamingContainer}. (that's just
     * required by composite component)
     *
     * @return
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Set the selected and available values of the day, month and year fields
     * based on the model.
     *
     * @param context
     * @throws java.io.IOException
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        if (getRootNode() == null) {
            setRootNode(createNodes());
        }
        super.encodeBegin(context);
    }

    public void updateSelectionMenus(FacesContext context, List<Menu> selectionMenus) {
        ValueExpression ve = this.getValueExpression("selectionMenus");

        if (ve != null) {
            ve.setValue(context.getELContext(), selectionMenus);
        } else {
            this.setSelectionMenus(selectionMenus);
        }
    }

    public TreeNode createNodes() {
        listMenu = profileEjb.getPerfilMenu(getIdProfile());

        TreeNode root = new CheckboxTreeNode();
        root.setExpanded(true);
        TreeNode node;
        for (Map.Entry<Sistema, List<RecursiveMap<Menu>>> entrySet : securityCatalogs.getSystemMenuMap().entrySet()) {
            node = new CheckboxTreeNode("sistema", entrySet.getKey(), root);
            node.setExpanded(true);
            printMenu(entrySet.getValue(), node);
        }

        return root;
    }

    private void printMenu(List<RecursiveMap<Menu>> listMap, TreeNode root) {
        TreeNode node;
        for (RecursiveMap<Menu> map : listMap) {
            for (Map.Entry<Menu, List<RecursiveMap<Menu>>> entrySet : map.entrySet()) {
                node = new CheckboxTreeNode("menu", entrySet.getKey(), root);
                node.setExpanded(true);
                if (listMenu != null) {
                    node.setSelected(listMenu.contains(entrySet.getKey()));
                }
                if (entrySet.getValue() != null && !entrySet.getValue().isEmpty()) {
                    printMenu(entrySet.getValue(), node);
                }
            }
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        super.processUpdates(context); //To change body of generated methods, choose Tools | Templates.

        List<TreeNode> selectNodesFull = new ArrayList<>();
        for (TreeNode node : getSelectedNodes()) {
            selectNodesFull.add(node);
            while (node.getParent() != null) {
                selectNodesFull.add(node.getParent());
                node = node.getParent();
            }
        }

        List<Menu> menus = new ArrayList<>();

        for (TreeNode node : selectNodesFull) {
            if (node == null) {
                continue;
            }

            if (node.getData() instanceof Menu && !menus.contains((Menu) node.getData())) {
                menus.add((Menu) node.getData());
            }
        }

        updateSelectionMenus(getFacesContext(), menus);
    }

}
