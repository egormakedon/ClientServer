package Controller.DialogAction;

import Model.*;

import java.util.ArrayList;

public class AddDialogAction {
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList run(ArrayList<String> arrayList) {
        TemplateClassAction templateClass = new TemplateClassAction();
        arrayList = templateClass.checkSameData(arrayList);

        if (arrayList.get(0).equals("1") && arrayList.size() == 1) return arrayList;

        for (int i = 7; i < 10; i++) {
            if (!checkString(arrayList.get(i))) {
                arrayList.clear();
                arrayList.add("2");
                return arrayList;
            }
        }

        model.addData(arrayList);

        ArrayList<Person> newList = new ArrayList<Person>();
        for (Person person : model.getPersons()) {
            newList.add(person);
        }

        return newList;
    }

    private boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}