package transmitter;

import org.jscience.mathematics.number.Complex;

public class Cell_Interleaver {
	
	public Complex[] do_cell_interleave(Complex[] cells) {
		int N_Cells = 8100;
		int N_d = 13;
		int p = (int)java.lang.Math.pow(2,N_d);
		Complex[] D_out = new Complex[N_Cells];   
		Complex[] cell_out = new Complex[cells.length];
		int data_length = cells.length;
		int blocks = data_length/N_Cells;
		int index = 0;
		
		for (int t = 0; t < blocks; t++) {
			Complex In[] = new Complex[N_Cells];
			for (int j = 0; j < N_Cells; j++) {
				index = t*N_Cells + j; 
				In[j] = cells[index];
			}			
				
			int S[][]= new int[p][N_d+1];
			for(int i=0;i<p;i++){
				S[i][0]=i;
			}
			
			for(int i=0;i<p;i++){
				S[i][1]=i%2;
			}

			S[2][N_d]=1;
			
			for(int i=3;i<p;i++){
				for(int j=2;j<N_d+1;j++){
					if(j==2){
						S[i][2]= S[i-1][N_d] ^ S[i-1][N_d-1] ^ S[i-1][N_d-4] ^ S[i-1][N_d-6] ;
					}
					else{
						S[i][j]=S[i-1][j-1];
					}
				}
			}
			
			int q=0;
			int[] L = new int[N_Cells];
			for(int i=0;i<p;i++){
				for(int j=N_d;j>0;j--){
					int d=13-j;
					L[q] += S[i][j]*(int)java.lang.Math.pow(2,d);
				}
			if(L[q]<N_Cells){
					q++;
				}
			else{
				L[q]=0;
			}
			
			}
			
			
			    
			for(int i=0;i<N_Cells;i++){         
				D_out[i]=In[L[i]];                         //Procedure of interleaving
				cell_out[t*N_Cells + i] = D_out[i];
			}
			
//			int[] Deint_out = new int[N_Cells];     //Parameters for deinterleave
//			for(int i=0;i<N_Cells;i++){
//				Deint_out[L[i]]=D_out[i];                   //Procedure of deinterleaving
//			}
			
			for(int i=0;i<N_Cells;i++){
				System.out.print(In[i]+"   ");
//				System.out.print(L[i]+"   ");
				System.out.println(D_out[i]+"   ");
//				System.out.println(Deint_out[i]);
			}
			
		}

		return cell_out;

	}

}
