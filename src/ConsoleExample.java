import java.util.Scanner;

public class ConsoleExample {
    double money, bet = 0.2, k, a, clear, nakoplenie=0;
    int choose, time;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleExample obj = new ConsoleExample();
        obj.input();
        obj.calculation();
    }

    public void input() {
        System.out.println("Введите сумму кредита");
        money = sc.nextInt();
        System.out.println("Введите срок погашения кредита (в месяцах)");
        time = sc.nextInt();
    }

    public void calculation() {
        System.out.println("Выберите схему погашения кредита: равными долями(1), начисление процентов на остаток кредита(2)");
        choose = sc.nextInt();
        switch (choose) {
            case (1):
                a = bet / 12.;
                k = (a*Math.pow((1 + a), time)) / (Math.pow((1 + a), time) - 1);
                nakoplenie = money * k;
                System.out.println("Ежемесячный платеж составит: " + nakoplenie);
                money = nakoplenie*time;
                System.out.println("Вообщем сумма составит: " + money);
                break;
            case (2):
                double[] doplata = new double[time];
                clear = money / time;
                for (int i = 0; i < time; i++) {
                    doplata[i] = money * bet * 31 / 365;
                    money -= clear;
                    nakoplenie += clear+doplata[i];
                }
                System.out.println("Вообщем сумма составит: " + nakoplenie);
                break;
        }
    }
}

