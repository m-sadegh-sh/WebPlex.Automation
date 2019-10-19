package ir.webplex.android.core.helpers;

import java.util.ArrayList;

import ir.webplex.android.api.responses.FindLetterItemResponse;
import ir.webplex.android.api.responses.FindLettersResponse;
import ir.webplex.android.api.responses.FindReceiverItemResponse;
import ir.webplex.android.api.responses.FindReceiversResponse;
import ir.webplex.android.api.responses.FindReceptorItemResponse;
import ir.webplex.android.api.responses.FindReceptorsResponse;
import ir.webplex.android.automation.activities.LettersActivity;
import ir.webplex.android.automation.activities.ReceiversActivity;
import ir.webplex.android.automation.activities.ReceptorsActivity;
import ir.webplex.android.automation.adapters.LettersAdapter;
import ir.webplex.android.automation.adapters.ReceiversAdapter;
import ir.webplex.android.automation.adapters.ReceptorsAdapter;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.models.ReceiverItem;
import ir.webplex.android.automation.models.ReceptorItem;

public class MappingUtils {
    public static void updateAdapter(LettersActivity activity, FindLettersResponse response) {
        ArrayList<LetterItem> items = new ArrayList<>();
        for (FindLetterItemResponse item : response.getItems())
            items.add(responseToModel(item));

        LettersAdapter adapter = (LettersAdapter) activity.getListAdapter();

        if (adapter == null)
            activity.setListAdapter(new LettersAdapter(items));
        else
            adapter.appendItems(items);
    }

    public static void updateAdapter(ReceptorsActivity activity, FindReceptorsResponse response) {
        ArrayList<ReceptorItem> items = new ArrayList<>();
        for (FindReceptorItemResponse item : response.getItems())
            items.add(responseToModel(item));

        ReceptorsAdapter adapter = (ReceptorsAdapter) activity.getListAdapter();

        if (adapter == null)
            activity.setListAdapter(new ReceptorsAdapter(items));
        else
            adapter.appendItems(items);
    }

    public static void updateAdapter(ReceiversActivity activity, FindReceiversResponse response) {
        ArrayList<ReceiverItem> items = new ArrayList<>();
        for (FindReceiverItemResponse item : response.getItems())
            items.add(responseToModel(item));

        ReceiversAdapter adapter = (ReceiversAdapter) activity.getListAdapter();

        if (adapter == null)
            activity.setListAdapter(new ReceiversAdapter(items));
        else
            adapter.appendItems(items);
    }

    private static LetterItem responseToModel(FindLetterItemResponse item) {
        return new LetterItem(
                item.getLetterGuid(),
                item.getSubject(),
                item.getIndicatorDate(),
                item.getIndicatorNumber(),
                item.getReceiversCount()
        );
    }

    private static ReceptorItem responseToModel(FindReceptorItemResponse item) {
        return new ReceptorItem(
                item.getPostId(),
                item.getTitle(),
                item.getEmail()
        );
    }

    private static ReceiverItem responseToModel(FindReceiverItemResponse item) {
        return new ReceiverItem(
                item.getTitle(),
                item.getEmail(),
                item.getRelativeDate()
        );
    }
}
