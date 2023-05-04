    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;

    import javax.lang.model.util.ElementScanner6;

    public class BNet {

        private int[][] counts = new int[2][2];
        private int[][][] fcCounts = new int[2][2][2];
        private int[][][] gfCounts = new int[2][2][2];
        //private int[][][] cgCounts = new int[2][2][2];
        private int[][] cgCounts = new int[2][2];
        private Double pB;
        private Double pC;
        double pGGivenB;
        double pGGivenBF;
        double pFGivenGTCT;
        double pFGivenGTCF;
        double pFGivenGFalseCTrue;
        double pFGivenGFalseCFalse;

        public BNet(String filename) {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.trim().split("\\s+");
                    int b = Integer.parseInt(data[0]);
                    int g = Integer.parseInt(data[1]);
                    int c = Integer.parseInt(data[2]);
                    int f = Integer.parseInt(data[3]);

                    counts[b][0]++;
                    counts[b][1]++;
                    fcCounts[f][c][g]++;
                    gfCounts[g][b][0]++;
                    gfCounts[g][b][1]++;
                    cgCounts[c][0]++;
                    cgCounts[c][1]++;
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                System.exit(1);
            }
        }

        public void printTables() {
            // P(B)
            pB = (double) counts[1][0] / (counts[0][0] + counts[1][0]);
            System.out.println("\nP(B): " + pB);
            System.out.println("P(B'):"+(1-pB));
        
            // P(G|B)
            pGGivenB = 2*(double) gfCounts[1][1][1] / (counts[1][0] + counts[1][1]);
            pGGivenBF = 2*(double) gfCounts[1][0][1] / (counts[0][0] + counts[0][1]);
            
            System.out.println("\nP(G|B is True): " + pGGivenB);
            System.out.println("(G|B is false): " + pGGivenBF);
            
            // P(G'|B)
            System.out.println("P(G'|B is True): " + (1-pGGivenB));
            System.out.println("P(G'|B is False): " + (1-pGGivenBF));
        
            // P(C)
            pC = (double) cgCounts[1][0] / (cgCounts[0][0] + cgCounts[1][0]);
            System.out.println("\nP(C): " + pC);
            System.out.println("P(C'):"+(1-pC));
        
            // P(F|G,C)
            pFGivenGTCT = (double) fcCounts[1][1][1] / (fcCounts[0][1][1] + fcCounts[1][1][1]);
            System.out.println("\nP(F|G = True,C=True): " + pFGivenGTCT);
        
            // P(F'|G,C)
            double pFNotGivenGTCT = 1 - pFGivenGTCT;
            System.out.println("P(F'|G=True,C=True): " + pFNotGivenGTCT);

            pFGivenGTCF = (double) fcCounts[1][0][1] / (fcCounts[0][0][1] + fcCounts[1][0][1]);
            System.out.println("P(F|G=True,C=False):"+pFGivenGTCF);
            System.out.println("P(F'|G=True,C=False):"+(1-pFGivenGTCF));

            pFGivenGFalseCTrue = (double) fcCounts[1][1][0] / (fcCounts[0][1][0] + fcCounts[1][1][0]);
            System.out.println("P(F|G=False,C=True): " + pFGivenGFalseCTrue);
            System.out.println("P(F'|G=False,C=True): " + (1-pFGivenGFalseCTrue));


            pFGivenGFalseCFalse = (double) fcCounts[1][0][0] / (fcCounts[0][0][0] + fcCounts[1][0][0]);
            System.out.println("P(F|G=False,C=False): " + pFGivenGFalseCFalse);
            System.out.println("P(F'|G=False,C=False): " + (1-pFGivenGFalseCFalse)+"\n");

        }
        
        
        public static void main(String[] args) {
            
            try{
                String filename = args[0];
                BNet bnet = new BNet(filename);
                
                if (args.length>1 && args.length < 5) {
                    System.err.println("Usage: java BNet <training_data> <Bt/Bf> <Gt/Gf> <Ct/Cf> <Ft/Ff>");
                    System.exit(1);
                }

                else if (args.length == 5)
                {
                    // Parse command line arguments
                boolean b = args[1].equalsIgnoreCase("Bt");
                boolean g = args[2].equalsIgnoreCase("Gt");
                boolean c = args[3].equalsIgnoreCase("Ct");
                boolean f = args[4].equalsIgnoreCase("Ft");
                    
                    bnet.printTables();

                double pB1=0;
                double pC1=0;
                double pG1=0;
                double pF1=0;
                
                // Calculate joint probability distribution
                if(b==true)
                {
                    pB1=bnet.pB;
                }

                else if(b==false)
                {
                    pB1=1-bnet.pB;
                }

                if(c==true)
                {
                    pC1=bnet.pC;
                }

                else if(c==false)
                {
                    pC1=1-bnet.pC;
                }

                if(g==true && b==true)
                {
                    pG1 = bnet.pGGivenB;
                }

                else if(g==true && b==false)
                {
                    pG1=bnet.pGGivenBF;
                }

                else if(g==false && b==true)
                {
                    pG1 = 1-bnet.pGGivenB;
                }

                else if(g==false && b==false)
                {
                    pG1 = 1-bnet.pGGivenBF;
                }

                if(g==true && c==true && f==true)
                {
                    pF1=bnet.pFGivenGTCT;
                }

                else if(g==true && c==true && f==false)
                {
                    pF1=1-bnet.pFGivenGTCT;
                }

                else if(g==true && c==false && f==true)
                {
                    pF1=bnet.pFGivenGTCF;
                }

                else if(g==true && c==false && f==false)
                {
                    pF1=1-bnet.pFGivenGTCF;
                }

                else if(g==false && c==true && f==true)
                {
                    pF1=bnet.pFGivenGFalseCTrue;
                }

                else if(g==false && c==true && f==false)
                {
                    pF1=1-bnet.pFGivenGFalseCTrue;
                }

                else if(g==false && c==false && f==true)
                {
                    pF1=bnet.pFGivenGFalseCFalse;
                }

                else if(g==false && c==false && f==true)
                {
                    pF1=1-bnet.pFGivenGFalseCFalse;
                }


                System.out.println("The joint probability for the required distribution is :"+(pB1*pC1*pG1*pF1+"\n"));

            // System.out.println("P(B=" + (b ? "t" : "f") + ",G=" + (g ? "t" : "f") + ",C=" + (c ? "t" : "f") + ",F=" + (f ? "t" : "f") + ") = " + pBGCTF);

                }

                else if (args.length == 1)
                {
                    bnet.printTables();
                }

                else
                {
                    System.err.println("Usage: java BNet <training_data>");
                    System.exit(1);
                }
            }
            
            catch(Exception e){
                System.out.println("Error in the command line input");
            }
        }
    }
        