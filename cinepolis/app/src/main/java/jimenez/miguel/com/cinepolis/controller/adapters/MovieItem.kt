package jimenez.miguel.com.cinepolis.controller.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.items.AbstractItem
import jimenez.miguel.com.cinepolis.R
import jimenez.miguel.com.cinepolis.model.entities.Movie


/**
 * Created by migueljimenez on 11/30/17.
 */
class MovieItem : AbstractItem<MovieItem, MovieItem.ViewHolder>() {

    var item: Movie? = null

    override fun getViewHolder(view: View?): ViewHolder = ViewHolder(view!!)

    override fun bindView(viewHolder: ViewHolder, payloads: List<Any>) {
        super.bindView(viewHolder, payloads)
        viewHolder.bind(item!!)
    }

    override fun getType(): Int = R.id.movie_item_adapter

    override fun getLayoutRes(): Int = R.layout.cell_movies

    fun getId(): Int = item?.id ?: -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @JvmField
        @BindView(R.id.txt_title)
        var txtTitle: TextView? = null

        @JvmField
        @BindView(R.id.txt_rating)
        var txtRating: TextView? = null

        @JvmField
        @BindView(R.id.txt_length)
        var txtLenght: TextView? = null

        @JvmField
        @BindView(R.id.txt_genre)
        var txtGenre: TextView? = null

        @JvmField
        @BindView(R.id.img_poster)
        open var imgPoster: ImageView? = null


        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(item: Movie) {
            txtTitle?.text = item.name
            txtRating?.text = item.rating
            txtLenght?.text = item.length
            txtGenre?.text = item.genre

            Glide.with(itemView)
                    .load("https://dsoh5jykc8zh3.cloudfront.net/resources/mx/movies/posters/94x137/" + (item.media?.get(0)?.resource ?: ""))
                    .into(imgPoster)

        }
    }

}