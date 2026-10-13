package OOP.OOP;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Analysis
{
    Department dept;
    private ArrayList<Workload> workloads;
    private ArrayList<Bedpercentage> bedPercentages;
    private ArrayList<Demandpercentages> demandPercentages;
    void analyseDepartment(Department dept)
    {
        this.dept=dept;
        String deptname=dept.getName();
        ArrayList<Doctor> doctors=dept.getDoctors();
        ArrayList<Bedrecords> bedrecords=dept.getBedrecords();
        ArrayList<Demand> demands=dept.getDemand();
        try
        {
            workloads = demandAnalysis(doctors, deptname);
        }
        catch (ArithmeticException e)
        {
            System.out.println("ERROR: " + e.getMessage());
            workloads = new ArrayList<>();
        }
        try
        {
            bedPercentages = demandAnalysis(deptname, bedrecords);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("ERROR: " + e.getMessage());
            bedPercentages = new ArrayList<>();
        }
        try
        {
            demandPercentages = demandAnalysis(demands);
        }
        catch (ArithmeticException e)
        {
            System.out.println("ERROR: " + e.getMessage());
            demandPercentages = new ArrayList<>();
        }

    }
    ArrayList<Workload> getWorkloads()
    {
        return workloads;
    }

    ArrayList<Bedpercentage> getBedPercentages()
    {
        return bedPercentages;
    }

    ArrayList<Demandpercentages> getDemandPercentages()
    {
        return demandPercentages;
    }

    ArrayList<Workload> demandAnalysis(ArrayList<Doctor> doctors,String departmentName)
    {
        System.out.print("===**"+departmentName+"**===");
        System.out.println();
        ArrayList<Workload> workloads=new ArrayList<>();
        System.out.println();

        if (doctors == null || doctors.isEmpty())
        {
            throw new ArithmeticException("No doctors found for " + departmentName);
        }
        ArrayList<LocalDate> proceesedDates=new ArrayList<>();

        for(int i=0;i<doctors.size();i++)
        {
            int count = 0;
            int total = 0;
            int count1 = 0;
            int total1 = 0;
            int count2 = 0;
            int total2 = 0;
            LocalDate date=doctors.get(i).getDate();
            if(proceesedDates.contains(date))
            {
                continue;
            }
            proceesedDates.add(date);
            for(Doctor doctor:doctors)
            {
                if(doctor.getDate().equals(date))
                {


                    if(doctor.getStartTime().equals(LocalTime.of(9,0)) && doctor.getEndTime().equals(LocalTime.of(12, 0)))
                    {
                        count++;
                        total=total+doctor.getpatientCount();
                    }

                    else if(doctor.getStartTime().equals(LocalTime.of(12, 0))&& doctor.getEndTime().equals(LocalTime.of(15,0)))
                    {
                        count1++;
                        total1=total1+doctor.getpatientCount();
                    }
                    else if(doctor.getStartTime().equals(LocalTime.of(15, 0))&& doctor.getEndTime().equals(LocalTime.of(18,0)))
                    {
                        count2++;
                        total2=total2+doctor.getpatientCount();
                    }
                }
            }



            double ratio=(double)total/count;
            double ratio1=(double)total1/count1;
            double ratio2=(double)total2/count2;
            double dailyavg=(double)(ratio+ratio1+ratio2)/3;
            Workload workload=new Workload(date,ratio, ratio1, ratio2, dailyavg);
            workloads.add(workload);

        }
        return workloads;

    }

    ArrayList<Bedpercentage> demandAnalysis(String departmentName,ArrayList<Bedrecords> bedrecords)
    {

        ArrayList<Bedpercentage> occupancyPercentages = new ArrayList<>();
        ArrayList<String> proceesedMonths=new ArrayList<>();
        for (int i = 0; i < bedrecords.size(); i++)
        {
            int total = bedrecords.get(i).getbedTotal();
            int occupied = bedrecords.get(i).getbedOccupied();
            String month=bedrecords.get(i).getMonth();
            if(total<=0)
            {
                throw new IllegalArgumentException("Total beds must be greater than zero");
            }
            if(proceesedMonths.contains(month))
            {
                continue;
            }
            proceesedMonths.add(month);
            double occupiedPerc=((double)occupied/total)*100;
            Bedpercentage bp=new Bedpercentage(occupiedPerc,month);
            occupancyPercentages.add(bp);

        }

        return occupancyPercentages;
    }
    ArrayList<Demandpercentages> demandAnalysis(ArrayList<Demand> demand)
    {
        ArrayList<Demandpercentages> demandpercentages = new ArrayList<>();
        for (Demand demand1 : demand)
        {
            String surgeryName = demand1.getsurgeryName();

            int unmet = demand1.getunmetCount();
            int performed = demand1.getperformedCount();
            int total = unmet + performed;

            if (total == 0)
            {
                throw new ArithmeticException(
                        "Total demand cannot be zero for " + surgeryName);
            }
            double unmetPercentage =((double) unmet / total) * 100;
            double performedPercentage = ((double) performed / total) * 100;
            int equipmentCount=demand1.getequipmentCount();
            Demandpercentages D1 =new Demandpercentages(surgeryName,unmetPercentage,performedPercentage,equipmentCount,total);
            demandpercentages.add(D1);
        }
        return demandpercentages;
    }




}
