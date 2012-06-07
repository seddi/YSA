package yapay_sinir_aglari;
import java.util.List;
import java.util.ArrayList;

/* Her proses elemanı bu sınıftan uretilecek */
public class Neuron {
	private List<Double> weights;
	private String classTarget;
	private int numWeights; 		/* Referans vektoru icin agırlıkların sayısı */
	public double lambda;
	public double winProb;			/* Bir prosesin kazanma olasılıgı */ 
	public boolean winState;
	public double punishValue;           /* Bir prosesin Ceza degeri */

	/* Agırlıkları rastgele olarak ilklendirmek icin kullanılır */
	public Neuron(int numWeights, String target, double initLearningRate) {
		this.numWeights = numWeights;  /* gelen degiskeni sınıf degiskenine ata */
		classTarget = target;
		lambda = initLearningRate;
		winState = false;
		punishValue = 0;
		winProb = 0;
		weights = new ArrayList<Double>();
		for(int i = 0; i < numWeights; i++) {
			weights.add(Math.random()*1);
		}
	}
	/* Girdi degerlerini ilk agırlık degerleri olarak almak icin kullanılır */
	public Neuron(List<Double> w, String t) {
		numWeights = w.size();
		classTarget = t;
		weights = w;
	}
	
	public List<Double> getWeights() {
		return weights;
	}
	
	public String getClassTarget() {
		return classTarget;
	}
	
	public void printWeights() {
		for(int i = 0; i < numWeights; i++) {
			System.out.print(weights.get(i) + " ");
		}
		System.out.println();
	}
}
