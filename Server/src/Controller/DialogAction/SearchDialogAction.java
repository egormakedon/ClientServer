package Controller.DialogAction;

import Model.Model;

import java.util.ArrayList;

public class SearchDialogAction {
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList run(ArrayList<String> arrayList, int num) {
        TemplateClassAction templateClass = new TemplateClassAction();
        arrayList = templateClass.checkSameData(arrayList);

        if (arrayList.get(0).equals("1") && arrayList.size() == 1) return arrayList;

        ArrayList newList = templateClass.run(arrayList, num, model);
        return newList;
    }
}