package tjc.rug.ExpertSystem.model;

import tjc.rug.ExpertSystem.model.Model;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    public void testModel() {
        Model model = new Model();
        System.out.println(model.getTitle());
        Model model2 = new Model();
        System.out.println(model2.getTitle());
        for (int idx = 0; idx < 10; ++idx) {
            System.out.print(model.getQuestion(true).toString());
        }
    }
}
