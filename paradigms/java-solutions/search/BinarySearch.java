package search;

public class BinarySearch {
    static int[] arr;

    // Pred: arr - не возрастает(∀i>j=>arr[i]<=arr[j])
    // обозн: (min i:x>=arr[i])=ind
    static int iterativeUpperBound(int x) {
        int l = -1, r = arr.length;
        // Pred: l=-1&&r=arr.length && ∀i>j=>arr[i]<=arr[j] && (r'-l')<(r-l)
        while (r - l > 1) {
            // Pred: r-l>1 && r<=arr.length && l>=-1
            int m = l + (r - l) / 2;
            // Post: m>=0 && m<=arr.length && m>l && m<r(т.к. r-l>1)
            if (arr[m] <= x) {
                // Pred: l>=-1 && r<=arr.length && m∈(l,r) && x>=arr[m] => ind∈(l,m]
                r = m;
                // Post: l'=l && r'=m=l+(r-l)/2<r(т.к. r-l>=2) => интервал посика сократился(~вдвое от предыдушего)
            } else {
                // Pred: l>=-1 && r<=arr.length && m∈(l,r) && x>=arr[m] => ind∈(m,r]
                l = m;
                // Post: r'=r && l'=m=l+(r-l)/2<r(т.к. r-l>=2) => интервал посика сократился(~вдвое от предыдушего)
            }
        }
        //Post: r-l<=1 && ∀i<r arr[i]>x && ∀j>=r arr[j]<=x

        return r;
    }
    // Post: min i: x>=arr[i]

    // Pred: arr - не возрастает(∀i>j=>arr[i]<=arr[j]) && l>=-1&&r<=arr.length && r-l>=1
    // обозн: (min i:x>=arr[i])=ind
    static int recursiveUpperBound(int x, int l, int r) {
        if (r - l <= 1) {
            // Pred: && r-l>=1 && r-l<=1 =>r-l=1 => {arr[l], arr[r]}&& ind∈(l,r]=>ind=r
            return r;
            // Post: ind=r
        }
        // Pred: r-l>1&&r<=arr.length&&l>=-1
        int m = l + (r - l) / 2;
        // Post: m>=0 && m<=arr.length && m>l && m<r(т.к. r-l>1)
        if (arr[m] <= x) {
            // Pred: l>=-1 && r<=arr.length && m∈(l,r) && x>=arr[m] => ind∈(l,m] && |(l,m]|<|(l,r]|
            return recursiveUpperBound(x, l, m);
            // Post: ind: ind∈(l,m]
        } else {
            // Pred: l>=-1 && r<=arr.length && m∈(l,r) && x>=arr[m] => ind∈(m,r] && |(m,r]|<|(l,r]|
            return recursiveUpperBound(x, m, r);
            // Post: ind: ind∈(m,r]
        }
    }
    // Post: min i: x>=arr[i],x∈(l,r]

    public static void main(String[] args) {
        //Pred: true
        int x = Integer.parseInt(args[0]);
        //Post: x=x

        arr = new int[args.length - 1];
        int sum = 0;
        // Pred: arr.length=args.length-1 && i=0 && i'=i+1<arr.length
        for (int i = 0; i < arr.length; i++) {
            // Pred: i<arr.length && args[i+1] is correct
            arr[i] = Integer.parseInt(args[i + 1]);
            // Post: arr[i]=args[i+1] && arr[i]<=arr[i-1](if i>=1), arr[i]=max(arr) if i=0
            sum += arr[i];
        }
        // Post: i>=arr.length=args.length-1 && ∀i>j arr[i]<=arr[j]

        if (sum % 2 == 0) {
            // Pred: ∀i>j=>arr[i]<=arr[j] && initialized x && sum%2=0
            System.out.print(recursiveUpperBound(x, -1, arr.length));
            // Post: printed i: ∀k<i arr[k]>x && ∀j>=i arr[j]<=x
        } else {
            // Pred: ∀i>j=>arr[i]<=arr[j] && initialized x && sum%2==1
            System.out.print(iterativeUpperBound(x));
            // Post: printed i: ∀k<i arr[k]>x && ∀j>=i arr[j]<=x
        }


    }

}
