package yapay_sinir_aglari;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class LVQ_X {
	private int numClass;
        private int sizeTarget;
	private Neuron[] khonenNeurons;		/* Kohonen katmanı prosesleri (nöron) */
	private int numFeature;
	private double initLearningRate = 0.05;  /* ogrenme katsayısı ilklendir */
	private double alpha = 0;
	private double decayRate = 0.001;        /* ogrenme katsayısı icin azaltma katsayısı */
	private List<String> target;
	public int numCorrectClasification = 0;
	private int numWinner = 0;
	private int numKhonenNeuron = 1;        /* Khonen Proses sayısı */
	private int numNeuronOfClass = 1;       /* bir sınıfı temsil edecek neuron sayısı */
	private String correctClass;		/* Olması beklenen sınıf degiskeni */
	private double punishFactor = 20;      /* Ceza katsayı sabiti */
	private double B = 0.0001;			/* verilerdeki dalgalanmayı gidermek icin sabit */
	private double distErr = 0.01;			/* Proseslere olan uzaklıgındaki Hata miktarı */
	private int egitDataSize = 1;
        /* Verileri 0-1 aralıgında isleme almak icin optimizasyon yapılır
         Bunun icin Egitim verilerindeki sınır degeri olabilecek MAX deger
         */
        public static int MAX_VALUE;           

	/* Kohonen katmanı proses elemanları (noronları) */
	public LVQ_X(List<List<Double>> initVector, List<String> target) {
		this.target = target;
		sizeTarget = target.size();         				/* sınıf sayısı */
                ArrayList <String> targetItem = new ArrayList<String>();
                for (int i = 0; i < sizeTarget; i++){
                    if (targetItem.indexOf(target.get(i)) == -1){
                        targetItem.add(target.get(i));
                    }
                }
                numClass = targetItem.size();
//		String line = null /* kac Kohonen prosesi 1 sınıfı temsil edecek */;
		/* kac Kohonen prosesi 1 sınıfı temsil edecek */
//		try {
		//	System.out.print(" 1 sınıfı temsil edecek proses sayısı : ");
		//	BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		//	line = is.readLine();
		numNeuronOfClass = Integer.parseInt(LVQ_X_Ceza.line);
//		} catch (NumberFormatException ex) {
//			System.err.println("Not a valid number: " + line);
//		} catch (IOException e) {
//			System.err.println("Unexpected IO ERROR: " + e);
//		}

		numKhonenNeuron = numClass * numNeuronOfClass;			/* khonen proses sayısı */
		khonenNeurons = new Neuron[numKhonenNeuron];			/* sınıf sayısı kadar cıktı prosesi (nöron) olustur */

		int n = 0;
                System.out.println(" sınıf sayısı : " + numClass);
                
		for(int i = 0; i < numClass; i++) {
			/* numNeuronOfClass tane khonen prosesi i. cıktı sınıfını temsil ediyor */
			for(int j = 0; j < numNeuronOfClass; j++, n++) {
				khonenNeurons[n] = new Neuron(initVector.get(i).size(), targetItem.get(i), initLearningRate);
				khonenNeurons[n].winProb = 1/numKhonenNeuron;	/* Kazanma olasılıgı ilklendir */
				punishValue(khonenNeurons[n]);			/* İlk Ceza degerini hesapla */
				khonenNeurons[n].winState = false;		/* Kazanma durumu ilklendir */
			}
		}
	}

	public void epoch(List<List<Double>> data, List<String> target) {
		numCorrectClasification = 0;
		String winnerClass;  /* Kazanan prosesin sınıfı */
		egitDataSize = data.size();
		for(int i = 0; i < data.size(); i++) {
			/* System.out.println("Iterasyon "+i); */

			List<Double> vector = data.get(i);
			Neuron localWinner = null;
			Neuron globalWinner = null;

			globalWinner = findBestMatchingUnit(vector, "Global winner");
			/* printVector(vector); */
			winnerClass = globalWinner.getClassTarget();
			correctClass = target.get(i);

			/* winner proses(nöron) dogru sınıf ile eslesmisse weights yaklastırılır aksi halde uzaklastırılır */
			if(winnerClass.equals(correctClass)){
				List<Double> weights = globalWinner.getWeights();
				for(int j = 0; j < weights.size(); j++) {
					double weight = weights.get(j);
					weight = weight + globalWinner.lambda*(vector.get(j) - weight);
					weights.set(j, weight);
				}

				printVector(globalWinner.getWeights(), "############### Local-Global Winner weights : ");
				numCorrectClasification++;
			}
			/* global kazanan yanlıs sınıfla eslemisse yerel
			 * kazananı yaklastır global kazananı uzaklastır */
			else{
				List<Double> localWeights;
				List<Double> globalWeights = globalWinner.getWeights();
				try{
					localWinner = findBestMatchingUnit(vector, "Local winner");
					localWeights = localWinner.getWeights();
					for(int j = 0; j < globalWeights.size(); j++) {
						double globalWeight = globalWeights.get(j);
						double localWeight = localWeights.get(j);
						/* Global kazanan nöron uzaklastırılır. */
						globalWeight = globalWeight - globalWinner.lambda*(vector.get(j) - globalWeight);
						globalWeights.set(j, globalWeight);
						/* Local kazanan nöron yaklastırılır. */	
						localWeight = localWeight - localWinner.lambda*(vector.get(j) - localWeight);
						localWeights.set(j, localWeight);
					}
					printVector(globalWinner.getWeights(), "############### Global Loser weights : ");
					printVector(localWinner.getWeights(), "############### Local Winner weights : ");

					localWinner.winState = true;
					winProbability(localWinner);

					if (localWinner.lambda >= decayRate){
						localWinner.lambda = localWinner.lambda - decayRate;
					}

				}catch (NullPointerException e){
					System.err.println("Local winner bulunamadı : " + localWinner);
				}
			}
			globalWinner.winState = true;
			winProbability(globalWinner);
			/* winner prosesin ogrenme katsayısını azalt */
			if (globalWinner.lambda >= decayRate){
				globalWinner.lambda = globalWinner.lambda - decayRate;
			}
		}
	}

	// öklid mesafe hesaplama ve kazanan proses(nöron) bulma
	public Neuron findBestMatchingUnit(List<Double> vector, String GorL) {
		Neuron winnerNeuron = null;
		double minDistance = Double.MAX_VALUE;
		int startNeuronIndex = 0;
		numWinner = 0;

		/* Global kazanan ya da Local kazanan aranıyor */
		int numScanNeuron =  (GorL == "Local winner") ? numNeuronOfClass : numKhonenNeuron; 
		/* Eger Local kazanan aranıyorsa Dogru sınıfı temsil eden noronlar kacıncı indisten baslıyor */

		for (int i = 0; i < numKhonenNeuron && numScanNeuron == numNeuronOfClass; i++){
			if (correctClass == khonenNeurons[i].getClassTarget()) {
				startNeuronIndex = i;
				break;
			}
		}

		for(int i = startNeuronIndex; i < startNeuronIndex + numScanNeuron; i++) {
			double sum = 0;						/* girdi vektoru ile referans vektoru sarsındaki mesafe */
			List<Double> weights = khonenNeurons[i].getWeights();
			for(int j = 0; j < vector.size(); j++) {
				sum += Math.pow(weights.get(j) - vector.get(j), 2);	/* referans vektoru (agırlık vektoru) ile mesafe hesapla */
			}
			double dist = Math.sqrt(sum) + khonenNeurons[i].punishValue;	/* Ceza eklenmis uzaklık */
			if( dist < minDistance) {
				minDistance = dist;
				winnerNeuron = khonenNeurons[i];
				winnerNeuron.lambda = (dist < distErr) ? 0 : winnerNeuron.lambda;
				//System.out.println(" ####################   Kazanan Proses : "+ i + " Mesafesi : " + dist);
				numWinner = i;
			}
		}
		if(winnerNeuron == null) System.out.println("Local winner NULL ... : " + numWinner);
		return winnerNeuron;
	}

	/* test verilerini sınıflandırma */
	public String classification(List<Double> vector) {
		Neuron winnerNeuron = findBestMatchingUnit(vector, "Global winner");
		return winnerNeuron.getClassTarget();              /* kazanan prosesin (noron) sınıfını don */
	}

	public void testing(List<List<Double>> testinput) {
		int numCorrect = 0;    /* dogru sınfılandırılan girdi sayısı */

		for(int i = 0; i < testinput.size(); i++) {
			List<Double> vector = testinput.get(i);

			String resultClass = classification(vector);
                        
			/* winner prosesin degerlerini yazdır */
			for (int j = 0; j < testinput.get(i).size(); j++){
				resultClass += " " + Double.toString((testinput.get(i)).get(j)*MAX_VALUE);
			}
			System.out.println("SonucSınıf:  " + resultClass);
                        LVQ_X_Ceza.resultClass.add("Kazanan Proses : " + numWinner + "  SonucSınıf:  " + resultClass +"\n");
			
		}
		/* Agımızın dogruluk hesabı */
		//double accuracy = (double)numCorrectClasification / egitDataSize;	// testinput.size();
		// System.out.println("Dogruluk yuzdesi : " + (accuracy*100) + "%");
	}

	public void printWeights() {
		for(int i = 0; i < khonenNeurons.length; i++) {
			System.out.print(i+1 + ". Prosesin Agırlıkları : ");
			printVector(khonenNeurons[i].getWeights(),"");
		}
	}

	private void printVector(List<Double> vec, String msj) {
		System.out.print(msj);
		for(int i = 0; i < vec.size(); i++) {
			System.out.print(vec.get(i)+" ");
		}
		System.out.println();
	}

	/* Kazanma olasılıgını hesapla */
	private void winProbability(Neuron P){
		int y = (P.winState) ? 1 : 0;
		P.winProb = P.winProb + B*(y - P.winProb);
		P.winState = false;
	}

	/* Ceza degeri hesabı */
	private void punishValue(Neuron P){
		P.punishValue = punishFactor*(P.winProb + 1/numKhonenNeuron);/* Ceza degerini ilklendir */
	}

	public void setAlpha(double alpha) {
		initLearningRate = alpha;
	}

	public void setDecay(double decay) {
		decayRate = decay;
	}
}
