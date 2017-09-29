package Controller.DialogAction;

import Model.Model;

import java.util.ArrayList;

public class EraseDialogAction {
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList run(ArrayList<String> arrayList, int num) {
        TemplateClassAction templateClass = new TemplateClassAction();
        arrayList = templateClass.checkSameData(arrayList);

        if (arrayList.get(0).equals("1") && arrayList.size() == 1) return arrayList;

        ArrayList newList = templateClass.run(arrayList, num, model);
        if (!((newList.get(0).toString().equals("2") || newList.get(0).toString().equals("3")) && newList.size() == 1)) {
            for (Object obj : newList) {
                model.eraseData(obj);
            }
        }

        return newList;
    }
}