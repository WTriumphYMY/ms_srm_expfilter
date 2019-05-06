package cn.edu.nwpu.ms_srm_expfilter.service.impl;

import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_expfilter.repository.SrmExperimentRepository;
import cn.edu.nwpu.ms_srm_expfilter.service.ExperimentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExperimentDataServiceImpl
 * @Author: gd
 * @Date: 2019/4/26 17:41
 * @Version: v1.0
 * @Description:
 */
@Service
public class ExperimentDataServiceImpl implements ExperimentDataService {
    @Autowired
    SrmExperimentRepository srmExperimentRepository;
    @Override
    public void addExperimentData(File expFile, String srmName) {
        SrmExperiment srmExperiment = new SrmExperiment();
        String exp_time;
        String exp_pressure;
        String exp_force;

        try {
            BufferedReader br = new BufferedReader(new FileReader(expFile));
            exp_time = "";
            exp_pressure = "";
            exp_force = "";
            String line = br.readLine();
            while ((line = br.readLine())!=null) {
                String[] str = line.split("\\s+");
                exp_time += str[0];
                exp_time += ",";
                exp_pressure += str[1];
                exp_pressure += ",";
                exp_force += str[2];
                exp_force += ",";
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        exp_time = exp_time.substring(0,exp_time.length()-1);
        exp_pressure= exp_pressure.substring(0,exp_pressure.length()-1);
        exp_force = exp_force.substring(0,exp_force.length()-1);

        String gmtCreate = new Date().toString();
        srmExperiment.setExpForce(exp_force);
        srmExperiment.setExpPressure((exp_pressure));
        srmExperiment.setExpTime(exp_time);
        srmExperiment.setSrmName(srmName);
        srmExperiment.setGmtCreate(gmtCreate);
        System.out.println(gmtCreate);

        srmExperimentRepository.save(srmExperiment);
    }

    @Override
    public void deleteExperimentDataById(Integer id) {
        srmExperimentRepository.deleteById(id);
    }

    @Override
    public void updateExperimentDataById(SrmExperiment srmExperiment) {
        SrmExperiment oldSrmExperient = findSrmExperimentBySrmName(srmExperiment.getSrmName());
        srmExperiment.setPkId(oldSrmExperient.getPkId());
        srmExperimentRepository.save(srmExperiment);
    }

    @Override
    public SrmExperiment findSrmExperimentByPkId(Integer id) {
        return srmExperimentRepository.findSrmExperimentByPkId(id);
    }

    @Override
    public SrmExperiment findSrmExperimentBySrmName(String srmName) {
        return srmExperimentRepository.findBySrmName(srmName);
    }
    @Override
    public List<SrmExperiment> findAll(){
        return srmExperimentRepository.findAll();
    }
//    @Override
//    public  SrmExperiment smoothData(Integer id)  {
//        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(id);
//        String[] tString = srmExperiment.getExpTime().split(",");
//        String[] fString = srmExperiment.getExpForce().split(",");
//        String[] pString = srmExperiment.getExpPressure().split(",");
//        ArrayList<Double> tList = new ArrayList<>();
//        ArrayList<Double> fList = new ArrayList<>();
//        ArrayList<Double> pList = new ArrayList<>();
//
//        int len = tString.length;
//        int n=30;//平均值的数据个数
//        for(int x = 0; x<len; x++){
//            tList.add(Double.parseDouble(tString[x]));
//            if (x<n){
//
//                fList.add(Double.parseDouble(fString[x]));
//                pList.add(Double.parseDouble(pString[x]));
//
//            }
//            else{
//                double fsum = 0.0,psum = 0.0;
//                for(int y =0; y<n; y++){
//                    fsum+=Double.parseDouble(fString[x-y]);
//                    psum+=Double.parseDouble(pString[x-y]);
//                }
//                double fave = fsum/n;
//                double pave = psum/n;
//                fList.add(fave);
//                pList.add(pave);
//            }
//
//        }
//        srmExperiment.setExpTime(tList.toString().substring(1,tList.toString().length()-1));
//        srmExperiment.setExpPressure(pList.toString().substring(1,pList.toString().length()-1));
//        srmExperiment.setExpForce(fList.toString().substring(1,fList.toString().length()-1));
////        System.out.println(fList.toString());
//        return srmExperiment;
//    }
    @Override
    public double[][] getXMartrix(int m,int k) {
        int n = 2*m+1;
        double[][] XM = new double[n][k+1];
        for(int x = 0;x<n;x++){
            for(int y = 0;y<k+1;y++){
                XM[x][y]=Math.pow(x-m, y);
            }
        }
        return XM;
    }
    @Override
    public double[][] reverse(double[][] temp) {
        double[][] temp1=new double[temp[0].length][temp.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {

                temp1[j][i] = temp[i][j] ;
            }
        }
        return temp1;
    }
    @Override
    public double[][] getConfactor(double[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        double[][] newdata = new double[H-1][V-1];
        for(int i=0; i<newdata.length; i++) {
            if(i < h-1) {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i][j];
                    }else {
                        newdata[i][j] = data[i][j+1];
                    }
                }
            }else {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i+1][j];
                    }else {
                        newdata[i][j] = data[i+1][j+1];
                    }
                }
            }
        }

//		for(int i=0; i<newdata.length; i ++)
//			for(int j=0; j<newdata[i].length; j++) {
//				System.out.println(newdata[i][j]);
//			}
        return newdata;
    }
    @Override
    public  double[][] matrixResult(double a[][], double b[][]) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        int aa= a[0].length;
        int bb = b.length;
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = a.length;
        int x = b[0].length;
        double c[][] = new double[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                //c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }
    @Override
    public double getMartrixResult(double[][] data) {
        /*
         * 二维矩阵计算
         */
        if(data.length==1) return data[0][0];

        if(data.length == 2) {
            return data[0][0]*data[1][1] - data[0][1]*data[1][0];
        }
        /*
         * 二维以上的矩阵计算
         */
        double result = 0.0;
        int num = data.length;
        double[] nums = new double[num];
        for(int i=0; i<data.length; i++) {
            if(i%2 == 0) {
                nums[i] = data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }else {
                nums[i] = -data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }
        }
        for(int i=0; i<data.length; i++) {
            result += nums[i];
        }

//		System.out.println(result);
        return result;
    }
    @Override
    public double[][] getCoMatrix(int m, int k){
        double[][] xM = getXMartrix(m,k);
//        print1(xM);
        double[][] xx= matrixResult(reverse(xM),xM);
//        print1(xx);
//		double[][] xx_re=getReverseMatrix(xx);
        double[][] xx_re=getReverseMatrix(xx);
//        print1(xx_re);
        double[][] BM = matrixResult(matrixResult(xM, xx_re),reverse(xM));
//        print1(BM);
        return BM;
    }
    @Override
    public  double[][] getReverseMatrix(double[][] data) {
        double[][] newdata = new double[data.length][data[0].length];
        double A = getMartrixResult(data);
    //		System.out.println(A);
        for(int i=0; i<data.length; i++) {
            for(int j=0; j<data[0].length; j++) {
                if((i+j)%2 == 0) {
                    newdata[i][j] = getMartrixResult(getConfactor(data, i+1, j+1)) / A;
                }else {
                    newdata[i][j] = -getMartrixResult(getConfactor(data, i+1, j+1)) / A;
                }
            }
        }
        newdata = reverse(newdata);//转置矩阵

    //		showMaxtrix(newdata);
        return newdata;
    }
    @Override
    public  SrmExperiment smoothData(Integer id)  {
        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(id);
        String[] tString = srmExperiment.getExpTime().split(",");
        String[] fString = srmExperiment.getExpForce().split(",");
        String[] pString = srmExperiment.getExpPressure().split(",");
        ArrayList<Double> tList = new ArrayList<>();
        ArrayList<Double> fList = new ArrayList<>();
        ArrayList<Double> pList = new ArrayList<>();

        int len = tString.length;
        int m=7;
        int k = 2;
        double[][] WC = getCoMatrix(m,k);
        double psum = 0.0;
        double fsum = 0.0;
        for(int x = 0; x<len; x++){
            if (x<m){
                psum = 0.0;
                fsum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    psum+=WC[x][y]*Double.parseDouble(pString[y]);
                    fsum+=WC[x][y]*Double.parseDouble(fString[y]);

                }
                tList.add(Double.parseDouble(tString[x]));
                pList.add(psum);
                fList.add(fsum);
            }
            else if(x>=len-m){
                psum = 0.0;
                fsum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    psum+=WC[x-len+(2*m+1)][y]*Double.parseDouble(pString[len-(2*m+1)+y]);
                    fsum+=WC[x-len+(2*m+1)][y]*Double.parseDouble(fString[len-(2*m+1)+y]);

                }
                tList.add(Double.parseDouble(tString[x]));
                pList.add(psum);
                fList.add(fsum);
            }
            else {
                psum = 0.0;
                fsum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    psum+=WC[m][y]*Double.parseDouble(pString[x-m+y]);
                    fsum+=WC[m][y]*Double.parseDouble(fString[x-m+y]);

                }
                tList.add(Double.parseDouble(tString[x]));
                pList.add(psum);
                fList.add(fsum);
            }

        }

        srmExperiment.setExpTime(tList.toString().substring(1,tList.toString().length()-1));
        srmExperiment.setExpPressure(pList.toString().substring(1,pList.toString().length()-1));
        srmExperiment.setExpForce(fList.toString().substring(1,fList.toString().length()-1));
//        System.out.println(fList.toString());
        return srmExperiment;



    }
    @Override
    public void toFile(String path ,Integer id){
        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(id);
        String[] tString = srmExperiment.getExpTime().split(",");
        String[] fString = srmExperiment.getExpForce().split(",");
        String[] pString = srmExperiment.getExpPressure().split(",");

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(" t(s) ");
            bw.write(',');
            bw.write(" Pb(Mpa) ");
            bw.write(',');
            bw.write(" F(kN) ");
            bw.newLine();
            for (int x = 0; x<tString.length; x++) {
                bw.write(tString[x]);
                bw.write(',');
                bw.write(pString[x]);
                bw.write(',');
                bw.write(fString[x]);
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void smoothToFile(String path ,Integer id){
        SrmExperiment srmExperiment = smoothData(id);
        String[] tString = srmExperiment.getExpTime().split(",");
        String[] fString = srmExperiment.getExpForce().split(",");
        String[] pString = srmExperiment.getExpPressure().split(",");

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(" t(s) ");
            bw.write(',');
            bw.write(" Pb(Mpa) ");
            bw.write(',');
            bw.write(" F(kN) ");
            bw.newLine();
            for (int x = 0; x<tString.length; x++) {
                bw.write(tString[x]);
                bw.write(',');
                bw.write(pString[x]);
                bw.write(',');
                bw.write(fString[x]);
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


}
