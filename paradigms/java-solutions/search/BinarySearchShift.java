package search;

public class BinarySearchShift {
    static int[] arr;

    // Pred: arr[i]!=arr[j], i!=j && ∃!x: (arr[0..x) - убыв, arr[x..arr.len) -убыв, arr[x]=max(arr)) &&
    // ∀i∈[0,x),j∈[x,arr.len) arr[i]<arr[j]
    static int findShift() {
        int l = -1, r = arr.length;
        // Pred: l=-1&&r=arr.length && arr[i]!=arr[j], i!=j && (r'-l')<(r-l)
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (arr[m] < arr[arr.length - 1]) {
                // Pred: arr[i]!=arr[j], i!=j && l>=-1 && r<=arr.length && m∈(l,r) && arr[m]<arr[arr.len-1]
                // => shift∈(m,r]
                l = m;
                // Post: r'=r && l'=m=l+(r-l)/2<r(т.к. r-l>=2) => интервал посика сократился(~вдвое от предыдушего)
            } else {
                // Pred: arr[i]!=arr[j], i!=j && l>=-1 && r<=arr.length && m∈(l,r) && arr[m]>arr[arr.len-1]
                // => shift∈(l,m]
                r = m;
                // Post: l'=l && r'=m=l+(r-l)/2<r(т.к. r-l>=2) => интервал посика сократился(~вдвое от предыдушего)
            }
        }
        //Post: r-l<=1 && ∀i>j∈(0,r): arr[i]<arr[j] && ∀i>j∈[r,arr.len) arr[i]<arr[j] => shift=r

        return r;
    }
    // Post: shift

    public static void main(String[] args) {
        //Pred: true
        arr = new int[args.length];
        //int k = 0;
        // Pred: arr.length=args.length && i=0 && i'=i+1<arr.length
        for (int i = 0; i < arr.length; i++) {
            // Pred: i<arr.length && args[i+1] is correct
            arr[i] = Integer.parseInt(args[i]);
            // Post: arr[i]=args[i]
        }
        // Post: i>=arr.length=args.length && arr[i]!=arr[j], i!=j
        // && ∃!x: (arr[0..x) - убыв, arr[x..arr.len) -убыв, arr[x]=max(arr))(циклически)

        // Pred: arr[i]!=arr[j], i!=j && ∃!x: (arr[0..x) - убыв, arr[x..arr.len) -убыв, arr[x]=max(arr))
        System.out.print(findShift());
        // Post: printed shift
    }
}
