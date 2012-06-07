package yapay_sinir_aglari;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Tester {
	static File  egitFile;
	static File  testFile;

	static double succesRate = 30; /* % 70 basarı oranı*/

        static List<List<Double>> egitData = new ArrayList<List<Double>>();
        static List<List<Double>> testData = new ArrayList<List<Double>>();
        static List<String> target = new ArrayList<String>();
        static LVQ_X lvq_x;

        public static void egit(){

                readInput(egitData, target, egitFile.toString());
		
		/* her satırdaki rgb degerlerini input'a ekle */
		List<List<Double>> input = new ArrayList<List<Double>>();
		for (int i = 0; i < egitData.size(); i++){
			for (int j = 0; j < egitData.get(i).size(); j++){
				System.out.print(" " + (egitData.get(i)).get(j)*LVQ_X.MAX_VALUE);
			}
			System.out.println();
			input.add(egitData.get(i));
		}

		/* her satırdaki sınıf isimlerini initTarget'a ekle */
		List<String> initTarget = new ArrayList<String>();
		for (int i = 0; i < target.size(); i++){
			initTarget.add(target.get(i));
		}

		lvq_x = new LVQ_X(input, initTarget);

		// lvq_x.printWeights();
		int i = 0;
		while (lvq_x.numCorrectClasification < egitData.size()*succesRate/100){
			lvq_x.epoch(egitData, target);
		}

		lvq_x.printWeights();
		
		/* TEST : test verileri ile test edilecek */
		            
        }

        public static void test(){
            readInput(testData, target, testFile.toString());
            lvq_x.testing(testData);
        }

	public static void readInput(List<List<Double>> data, List<String> target, String fileName) {
		try {
			/* inputrgb dosyasi ilk 3 oge renk degerleri son oge hangi renk oldugudur. Dosyanın sutunları virgul ile ayrılmıstır*/
			File file = new File(fileName);
			BufferedReader bufreader = new BufferedReader(new FileReader(file)); /* dosyayı boyutu kadar buffera oku*/

			String line = new String("");
			System.out.println(">>>Start reading input...");
			int k = 0;
			while((line = bufreader.readLine()) != null) {   			/* bufferdaki dosya icerigini satır satır oku*/
				k += 1;
				String[] lines = line.split(",");     			/* satırı virgule(,) gore split et */
				List<Double> vector = new ArrayList<Double>();
				System.out.print("girdi " + k + " : ");   		/* her satırdaki girdiler icin on bilgi yaz, yanına rgb bilgileri yazılacak*/
				if (fileName == egitFile.toString()){
					for(int i = 0; i < lines.length; i++) {
						if(i != lines.length-1) {             		/*  */
							int d = Integer.parseInt(lines[i]);
							vector.add((double)d/LVQ_X.MAX_VALUE);            	/* rgb degerlerini 0-1 aralıgına sıkıstır ve vektor olustur */
							System.out.print(d + ",");        		/*(r,g,b degerleri) sutun bilgilerini yaz */
						} else {
							//int cl = Integer.parseInt(lines[i]);
							target.add(lines[i]);                   /* son sutun degeri sınıfını belirtir. target degiskeni ile don*/
							System.out.print(lines[i]);   			/* girdilerin sınıflarını yazdırır */
						}
					}
				}
				else if(fileName == testFile.toString()){
					for(int i = 0; i < lines.length; i++) {
						int d = Integer.parseInt(lines[i]);
						vector.add((double)d/LVQ_X.MAX_VALUE);            	/* rgb degerlerini 0-1 aralıgına sıkıstır ve vektor olustur */
						System.out.print(d + ",");        		/*(r,g,b degerleri) sutun bilgilerini yaz */
					}
				}
				data.add(vector);        /* rgb degerlerinden olusmus olan girdi vektorunu "data" degiskeni uzerinden geri don */
				System.out.println();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
