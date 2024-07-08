/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_gui;

/**
 *
 * @author amatibag
 */
public class PayrollCalculation {
    public static double calculateOvertimeEarnings(double otRate, double hourlyRate, double otHours) {
        return otRate * hourlyRate * otHours;
    }

    public static double calculateTaxableEarnings(double basicSalary, double adjEarnings) {
        return basicSalary + adjEarnings;
    }
    
    public static double calculateNetGross(double overtimeEarnings, double taxableEarnings) {
        return overtimeEarnings + taxableEarnings;
    }
    
    public static double calculateSSS(double netGross) {
        double sSS = 0;
        if (netGross <3250) {sSS = 135;}else{
        if (netGross >= 3250 && netGross<= 3750) {sSS = 157.5;}else{
        if (netGross >= 3750 && netGross<= 4250) {sSS = 180;}else{
        if (netGross >= 4250 && netGross<= 4750) {sSS = 202.5;}else{
        if (netGross >= 4750 && netGross<= 5250) {sSS = 225;}else{
        if (netGross >= 5250 && netGross<= 5750) {sSS = 247.5;}else{
        if (netGross >= 5750 && netGross<= 6250) {sSS = 270;}else{
        if (netGross >= 6250 && netGross<= 6750) {sSS = 292.5;}else{
        if (netGross >= 6750 && netGross<= 7250) {sSS = 315;}else{
        if (netGross >= 7250 && netGross<= 7750) {sSS = 337.5;}else{
        if (netGross >= 7750 && netGross<= 8250) {sSS = 360;}else{
        if (netGross >= 8250 && netGross<= 8750) {sSS = 382.5;}else{
        if (netGross >= 8750 && netGross<= 9250) {sSS = 405;}else{
        if (netGross >= 9250 && netGross<= 9750) {sSS = 427.5;}else{
        if (netGross >= 9750 && netGross<= 10250) {sSS = 450;}else{
        if (netGross >= 10250 && netGross<= 10750) {sSS = 472.5;}else{
        if (netGross >= 10750 && netGross<= 11250) {sSS = 495;}else{
        if (netGross >= 11250 && netGross<= 11750) {sSS = 517.5;}else{
        if (netGross >= 11750 && netGross<= 12250) {sSS = 540;}else{
        if (netGross >= 12250 && netGross<= 12750) {sSS = 562.5;}else{
        if (netGross >= 12750 && netGross<= 13250) {sSS = 585;}else{
        if (netGross >= 13250 && netGross<= 13750) {sSS = 607.5;}else{
        if (netGross >= 13750 && netGross<= 14250) {sSS = 630;}else{
        if (netGross >= 14250 && netGross<= 14750) {sSS = 652.5;}else{
        if (netGross >= 14750 && netGross<= 15250) {sSS = 675;}else{
        if (netGross >= 15250 && netGross<= 15750) {sSS = 697.5;}else{
        if (netGross >= 15750 && netGross<= 16250) {sSS = 720;}else{
        if (netGross >= 16250 && netGross<= 16750) {sSS = 742.5;}else{
        if (netGross >= 16750 && netGross<= 17250) {sSS = 765;}else{
        if (netGross >= 17250 && netGross<= 17750) {sSS = 787.5;}else{
        if (netGross >= 17750 && netGross<= 18250) {sSS = 810;}else{
        if (netGross >= 18250 && netGross<= 18750) {sSS = 832.5;}else{
        if (netGross >= 18750 && netGross<= 19250) {sSS = 855;}else{
        if (netGross >= 19250 && netGross<= 19750) {sSS = 877.5;}else{
        if (netGross >= 19750 && netGross<= 20250) {sSS = 900;}else{
        if (netGross >= 20250 && netGross<= 20750) {sSS = 922.5;}else{
        if (netGross >= 20750 && netGross<= 21250) {sSS = 945;}else{
        if (netGross >= 21250 && netGross<= 21750) {sSS = 967.5;}else{
        if (netGross >= 21750 && netGross<= 22250) {sSS = 990;}else{
        if (netGross >= 22250 && netGross<= 22750) {sSS = 1012.5;}else{
        if (netGross >= 22750 && netGross<= 23250) {sSS = 1035;}else{
        if (netGross >= 23250 && netGross<= 23750) {sSS = 1057.5;}else{
        if (netGross >= 23750 && netGross<= 24250) {sSS = 1080;}else{
        if (netGross >= 24250 && netGross<= 24750) {sSS = 1102.5;}else{
        if (netGross >= 24750) {sSS = 1125;}
        }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
        sSS = Math.round(sSS*100.0)/100.0;
        return sSS;
    }
    
    public static double calculatePhilHealth(double netGross) {
        double pHealthCalc = netGross * 0.03;
        double philHealth = 0;
        if (netGross < 10000) {philHealth = 0;}else{
        if (pHealthCalc > 1800) {philHealth = 1800 / 2;}else{
        if (netGross >= 10000 && netGross <= 60000) {philHealth = (netGross * 0.03) / 2;}
        }}
        
        philHealth = Math.round(philHealth*100.0)/100.0;
        return philHealth;
    }

    public static double calculatePagibig(double netGross) {
        double pIbigCalc1 = netGross * 0.01;
        double pIbigCalc2 = netGross * 0.02;
        double pagIbig = 0;
        if (netGross >=1000 && netGross <= 1500 && pIbigCalc1 > 100) {pagIbig = 100;}else{
        if (netGross >=1000 && netGross <= 1500 && pIbigCalc1 <= 100) {pagIbig = netGross * 0.01;}else{
        if (netGross >1500 && pIbigCalc2 > 100) {pagIbig = 100;}else{
        if (netGross >1500 && pIbigCalc1 <= 100) {pagIbig = netGross * 0.02;}else{
        if (netGross <1000) {pagIbig = 0;}
        }}}}
        
        pagIbig = Math.round(pagIbig*100.0)/100.0;
        return pagIbig;
    }

    public static double calculateWitholdingTax(double net) {
        double wTax = 0;
        if (net <= 20832) {wTax = 0;}else{
        if (net >= 20833 && net < 33333) {wTax = (net - 20833) * 0.20;}else{
        if (net >= 33333 && net < 66667) {wTax = 2500 + ((net - 33333) * 0.25);}else{
        if (net >= 66667 && net < 166667) {wTax = 10833 + ((net - 66667) * 0.30);}else{
        if (net >= 166667 && net < 666667) {wTax = 40833.33 + ((net - 166667) * 0.32);}else{
        if (net > 666667) {wTax = 200833.33 + ((net - 666667) * 0.35);}
        }}}}}
        wTax = Math.round(wTax*100.0)/100.0;
        return wTax;
    }

}

